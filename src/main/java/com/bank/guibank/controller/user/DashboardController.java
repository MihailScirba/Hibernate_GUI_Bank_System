package com.bank.guibank.controller.user;

import com.bank.guibank.controller.user.utils.TableRowData;
import com.bank.guibank.model.database.transaction.TransactionDAO;
import com.bank.guibank.model.transactions.Transaction;
import com.bank.guibank.model.transactions.utils.TransactionType;
import com.bank.guibank.model.user.User;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    private final String[] TRANSACTION_ERROR_MESSAGES = {
            "Invalid transaction type", "Not enough money",
            "Not entered amount", "Wrong number format",
            "Not received recipient data"
    };

    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#.##");

    @Setter
    private User user;

    @FXML
    public TableView<TableRowData> dataTableContainer;

    @FXML
    public GridPane transactionContainer;

    @FXML
    public ChoiceBox<String> operationType;

    @FXML
    public TextField amount;

    @FXML
    public VBox recipientBox;

    @FXML
    public TextField recipientFirstname;

    @FXML
    public TextField recipientLastname;


    @FXML
    public void initialize() {
        clearTransactionForm();
        clearPanel();

        dataTableContainer.
                setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        dataTableContainer.setFixedCellSize(89);
    }

    private void clearPanel() {
        dataTableContainer.setOpacity(0);
        dataTableContainer.setManaged(false);

        transactionContainer.setOpacity(0);
        transactionContainer.setManaged(false);
    }

    private void displayPanel(Node node) {
        node.setOpacity(1);
        node.setManaged(true);
        VBox.setVgrow(node, Priority.ALWAYS);
    }

    @FXML
    public void handleShowDataRequest() {
        clearPanel();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ObservableList<TableRowData> rows = FXCollections.observableArrayList(
                new TableRowData("idnp", user.getIdnp()),
                new TableRowData("firstname", user.getFirstname()),
                new TableRowData("lastname", user.getLastname()),
                new TableRowData("birthday", formatter.format(user.getBirthday())),
                new TableRowData("balance",
                        DECIMAL_FORMATTER.format(user.getBalance()) + " $")
        );
        dataTableContainer.setItems(rows);
        displayPanel(dataTableContainer);
    }

    @FXML
    public void showTransactionPanel() {
        clearPanel();
        displayPanel(transactionContainer);
    }

    @FXML
    public void setEnableRecipientBox() {
        if (operationType.getSelectionModel().getSelectedIndex() == 3) {
            recipientBox.setDisable(false);
        } else {
            recipientBox.setDisable(true);
            recipientFirstname.clear();
            recipientLastname.clear();
        }
    }

    @FXML
    public void clearTransactionForm() {
        operationType.getSelectionModel().clearAndSelect(0);
        amount.clear();
        recipientFirstname.clear();
        recipientLastname.clear();
    }

    @FXML
    public void handleTransactionRequest() {
        String transactionError = isValidTransactionInput();
        if (transactionError != null) {
            showErrorMessage(transactionError);
            return;
        }
        switch (operationType.getSelectionModel().getSelectedIndex()) {
            case 1 -> executeTransaction(TransactionType.DEPOSIT);
            case 2 -> executeTransaction(TransactionType.WITHDRAWAL);
        }
        clearTransactionForm();
    }

    private @Nullable String isValidTransactionInput() {
        if (operationType.getSelectionModel().getSelectedIndex() == 0) {
            return TRANSACTION_ERROR_MESSAGES[0];
        }
        if (amount.getText().isEmpty()) {
            return TRANSACTION_ERROR_MESSAGES[2];
        }
        try {
            var amount = Double.parseDouble(this.amount.getText());
            if (operationType.getSelectionModel().getSelectedIndex() != 1
                    && user.getBalance() < amount) {
                return TRANSACTION_ERROR_MESSAGES[1];
            }
        } catch (NumberFormatException e) {
            return TRANSACTION_ERROR_MESSAGES[3];
        }
        if (operationType.getSelectionModel().getSelectedIndex() == 3
                && !isValidRecipientBox()) {
            return TRANSACTION_ERROR_MESSAGES[4];
        }
        return null;
    }

    private boolean isValidRecipientBox() {
        return !recipientFirstname.getText().isEmpty()
                && !recipientLastname.getText().isEmpty();
    }

    private void executeTransaction(String transactionType) {
        var amount = Double.parseDouble(this.amount.getText());
        var transaction = new Transaction(amount, LocalDateTime.now(),
                transactionType, user);

        TransactionDAO transactionDAO = new TransactionDAO();
        if (transactionDAO.makeTransaction(transaction)) {
            showSuccessfulTransactionMessage();
        } else {
            showErrorMessage("Transaction failed. Please try again later!");
        }
    }

    private void showSuccessfulTransactionMessage() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        success.setHeaderText("About transaction");
        success.setContentText("Your transaction has been successfully "
                + "completed");
        success.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Error in transaction execution");
        error.setContentText(message);
        error.showAndWait();
    }

    @FXML
    public void logOutAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/MainPage.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, mainScene,
                StageTitles.MAIN_PAGE);
    }
}
package com.bank.guibank.controller.user;

import com.bank.guibank.controller.user.utils.TableRowData;
import com.bank.guibank.model.database.user.UserDAO;
import com.bank.guibank.model.user.User;
import com.bank.guibank.model.user.exceptions.NotEnoughMoneyException;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    private final String[] TRANSACTION_ERROR_MESSAGES = {
            "Invalid transaction type", "Not entered amount",
            "Wrong number format", "Not entered recipient data",
            "Something went wrong", "Recipient not found"
    };

    private final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#.##");

    @Setter
    private User user;

    @FXML
    public TableView<TableRowData> dataTableContainer;

    @FXML
    public GridPane transactionContainer;

    @FXML
    public Label balance;

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
    public VBox passwordChangeContainer;

    @FXML
    public PasswordField newPassword;

    @FXML
    public PasswordField newPasswordConfirmation;

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

        passwordChangeContainer.setOpacity(0);
        passwordChangeContainer.setManaged(false);
    }

    private void displayPanel(@NotNull Node node) {
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
        updateBalanceTextField();
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
    public void handleTransactionRequest() {
        String transactionError = isValidTransactionInput();
        if (transactionError != null) {
            showErrorTransactionMessage(transactionError);
            return;
        }
        var amount = Double.parseDouble(this.amount.getText());
        switch (operationType.getSelectionModel().getSelectedIndex()) {
            case 1 -> deposit(amount);
            case 2 -> withdraw(amount);
            case 3 -> transfer(amount);
        }
        clearTransactionForm();
    }

    private @Nullable String isValidTransactionInput() {
        if (operationType.getSelectionModel().getSelectedIndex() == 0) {
            return TRANSACTION_ERROR_MESSAGES[0];
        }
        if (amount.getText().isEmpty()) {
            return TRANSACTION_ERROR_MESSAGES[1];
        }
        try {
            Double.parseDouble(this.amount.getText());
        } catch (NumberFormatException e) {
            return TRANSACTION_ERROR_MESSAGES[2];
        }
        if (operationType.getSelectionModel().getSelectedIndex() == 3
                && !isValidRecipientBox()) {
            return TRANSACTION_ERROR_MESSAGES[3];
        }
        return null;
    }

    private boolean isValidRecipientBox() {
        return !recipientFirstname.getText().isEmpty()
                && !recipientLastname.getText().isEmpty();
    }

    private void deposit(double amount) {
        boolean transactionDone = user.deposit(amount);
        if (transactionDone) {
            showSuccessfulTransactionMessage();
            updateBalanceTextField();
        } else {
            showErrorTransactionMessage(TRANSACTION_ERROR_MESSAGES[4]);
        }
    }

    private void withdraw(double amount) {
        try {
            boolean transactionDone = user.withdraw(amount);
            if (transactionDone) {
                showSuccessfulTransactionMessage();
                updateBalanceTextField();
            } else {
                showErrorTransactionMessage(TRANSACTION_ERROR_MESSAGES[4]);
            }
        } catch (NotEnoughMoneyException e) {
            showErrorTransactionMessage(e.getMessage());
        }
    }

    private void transfer(double amount) {
        UserDAO dao = new UserDAO();
        User recipient = dao.getUserByFullName(
                recipientFirstname.getText(), recipientLastname.getText()
        );
        if (recipient == null) {
            showErrorTransactionMessage("Recipient no found");
            return;
        }
        try {
            boolean transactionDone = user.transfer(amount, recipient);
            if (transactionDone) {
                showSuccessfulTransactionMessage();
                updateBalanceTextField();
            } else {
                showErrorTransactionMessage(TRANSACTION_ERROR_MESSAGES[4]);
            }
        } catch (NotEnoughMoneyException e) {
            showErrorTransactionMessage(e.getMessage());
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

    private void updateBalanceTextField() {
        var userBalance = DECIMAL_FORMATTER.format(user.getBalance());
        balance.setText("Balance: " + userBalance + " $");
    }

    private void showErrorTransactionMessage(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Error in transaction execution");
        error.setContentText(message);
        error.showAndWait();
    }

    @FXML
    public void clearTransactionForm() {
        operationType.getSelectionModel().clearAndSelect(0);
        amount.clear();
        recipientFirstname.clear();
        recipientLastname.clear();
    }

    public void showChangePasswordPanel() {
        clearPanel();
        displayPanel(passwordChangeContainer);
    }

    @FXML
    public void handleChangePasswordRequest() {
        if (!isValidChangePasswordForm()) {
            showChangePasswordErrorMessage("One or more fields are empty");
            return;
        }
        if (!isValidNewPassword()) {
            showChangePasswordErrorMessage("Passwords are not equals");
            return;
        }
        if (user.changePassword(newPassword.getText())) {
            showChangePasswordSuccessfulMessage();
            clearPanel();
        } else {
            showChangePasswordErrorMessage("Something went wrong");
        }
    }

    private boolean isValidChangePasswordForm() {
        return !newPassword.getText().isEmpty()
                || !newPasswordConfirmation.getText().isEmpty();
    }

    private boolean isValidNewPassword() {
        return newPassword.getText().equals(newPasswordConfirmation.getText());
    }

    private void showChangePasswordErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Change password error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showChangePasswordSuccessfulMessage() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        success.setHeaderText("About changing password");
        success.setContentText("Password successfully changed");
        success.showAndWait();
    }

    @FXML
    public void handleRemoveAccountRequest(MouseEvent mouseEvent)
            throws IOException {
        if (user.removeAccount()) {
            showRemoveAccountSuccessfulMessage();
            logOut(mouseEvent);
        } else {
            showRemoveAccountErrorMessage();
        }
    }

    private void showRemoveAccountSuccessfulMessage() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        success.setHeaderText("Remove account");
        success.setContentText("Account successfully removed");
        success.showAndWait();
    }

    private void showRemoveAccountErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Remove account error");
        alert.setContentText("Something went wrong");
        alert.showAndWait();
    }

    @FXML
    public void logOut(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/MainPage.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, mainScene,
                StageTitles.MAIN_PAGE);
    }
}
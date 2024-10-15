package com.bank.guibank.controller.user;

import com.bank.guibank.controller.user.utils.TableRowData;
import com.bank.guibank.model.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class UserController {
    @FXML
    public TableColumn<TableRowData, String> propertyCol;

    @FXML
    public TableColumn<TableRowData, String> valueCol;

    @FXML
    public TableView<TableRowData> tableView;

    @Setter
    private User user;

    @FXML
    public void initialize() {
        propertyCol.setCellValueFactory(new PropertyValueFactory<>("property"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setFixedCellSize(89);
    }

    public void showData() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DecimalFormat formatter2 = new DecimalFormat("#.##");

        ObservableList<TableRowData> rows = FXCollections.observableArrayList(
                new TableRowData("idnp", user.getIdnp()),
                new TableRowData("firstname", user.getFirstname()),
                new TableRowData("lastname", user.getLastname()),
                new TableRowData("birthday", formatter.format(user.getBirthday())),
                new TableRowData("balance", formatter2.format(user.getBalance()))
        );
        tableView.setItems(rows);
    }
}

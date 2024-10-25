package com.bank.guibank.controller.user;

import com.bank.guibank.model.database.user.UserDAO;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ForgotPasswordController {

    private final String[] ERROR_MESSAGES = {
            "One or more fields are empty", "User not found",
            "Passwords are not equals"
    };

    @FXML
    public TextField firstname;

    @FXML
    public TextField lastname;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField passwordConfirmation;

    @FXML
    public void handleChangePasswordRequest(MouseEvent mouseEvent)
            throws IOException {
        if (isValidForm()) {
            showErrorMessage(ERROR_MESSAGES[0]);
            return;
        }
        UserDAO dao = new UserDAO();
        var user = dao.getUserByFullName(firstname.getText(), lastname.getText());
        if (user == null) {
            showErrorMessage(ERROR_MESSAGES[1]);
            return;
        }
        if (!isValidPassword()) {
            showErrorMessage(ERROR_MESSAGES[2]);
            return;
        }

        if (!user.changePassword(password.getText())) {
            showErrorMessage("Something went wrong");
        } else {
            showSuccessfulMessage();
            goToPreviousPage(mouseEvent);
        }
    }

    private boolean isValidForm() {
        return firstname.getText().isEmpty() || lastname.getText().isEmpty()
                || password.getText().isEmpty()
                || passwordConfirmation.getText().isEmpty();
    }

    private boolean isValidPassword() {
        return password.getText().equals(passwordConfirmation.getText());
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Change password error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessfulMessage() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        success.setHeaderText("About changing password");
        success.setContentText("Password successfully changed");
        success.showAndWait();
    }

    @FXML
    public void goToPreviousPage(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/Login.fxml"));
        Scene userScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, userScene,
                StageTitles.USER_LOGIN);
    }
}

package com.bank.guibank.controller.user;

import com.bank.guibank.model.user.User;
import com.bank.guibank.model.user.exceptions.UserHasAccountException;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Setter;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField passwordConfirmation;

    @FXML
    public Label errorMessage;

    @Setter
    private User user;

    @FXML
    public void goToPreviousPage(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/Login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(event, loginScene,
                StageTitles.USER_LOGIN);
    }

    @FXML
    public void handleRegisterRequest(Event event) {
        String username = this.username.getText();
        String password = isValidPasswordForm() ? this.password.getText() : null;

        if (isEmptyPasswordForm()) {
            showErrorMessage("Enter both passwords");
            return;
        }
        if (!isValidPasswordForm()) {
            showErrorMessage("Passwords do not match");
        }
        try {
            user.registerAccount(username, password);
            goToPreviousPage(event);
        } catch (UserHasAccountException e) {
            errorMessage.setOpacity(1);
        } catch (Exception e) {
            showErrorMessage("Something went wrong");
        }

    }

    private boolean isEmptyPasswordForm() {
        return this.password.getText() == null
                || this.password.getText().isEmpty()
                || this.passwordConfirmation.getText() == null
                || this.passwordConfirmation.getText().isEmpty();
    }

    private boolean isValidPasswordForm() {
        return this.password.getText()
                .equals(this.passwordConfirmation.getText());
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(StageTitles.USER_REGISTER);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package com.bank.guibank.controller.user;

import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterController {

    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public PasswordField passwordConfirmation;

    @FXML
    public Label errorMessage;


    @FXML
    public void goBackBtnAction(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/Login.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(event, mainScene,
                StageTitles.USER_LOGIN);
    }

    @FXML
    public void handleRegisterRequest(Event event) {
        String username = this.username.getText();
        String password = isValidPassword() ? this.password.getText() : null;
    }

    private boolean isValidPassword() {
        return this.password.getText()
                .equals(this.passwordConfirmation.getText());
    }
}

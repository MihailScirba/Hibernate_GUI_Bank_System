package com.bank.guibank.controller.user;

import com.bank.guibank.model.database.user.UserAccountDAO;
import com.bank.guibank.model.user.UserAccount;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField username;

    @FXML
    public PasswordField password;

    @FXML
    public Label errorMessage;


    @FXML
    public void goToPreviousPage(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/MainPage.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, mainScene,
                StageTitles.MAIN_PAGE);
    }

    @FXML
    public void handleLoginRequest(Event event) throws IOException {
        String usernameString = username.getText();
        String passwordString = password.getText();
        UserAccountDAO accountDAO = new UserAccountDAO();
        UserAccount userAccount = accountDAO
                .getUserAccountByUsername(usernameString);

        if (!isValidAccount(userAccount, passwordString)) {
            errorMessage.setOpacity(1);
        } else {
            errorMessage.setOpacity(0);
            goToUserView(event, userAccount);
        }
    }

    private void goToUserView(Event event, UserAccount userAccount)
            throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/Dashboard.fxml"));
        Parent root = loader.load();
        ((DashboardController) loader.getController())
                .setUser(userAccount.getUser());
        Scene userScene = new Scene(root);
        StageManagement.switchScene(event, userScene,
                StageTitles.USER_DASHBOARD);
    }

    private boolean isValidAccount(UserAccount userAccount, String password) {
        return userAccount != null && userAccount.getPassword().equals(password);
    }

    @FXML
    public void setForgotPassword(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/ForgotPassword.fxml"));
        Scene registerPage1 = new Scene(loader.load());
        StageManagement.switchScene(mouseEvent, registerPage1,
                StageTitles.USER_FORGOT_PASSWORD);
    }

    @FXML
    public void setRegisterBtnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/RegisterValidation.fxml"));
        Scene registerPage1 = new Scene(loader.load());
        StageManagement.switchScene(mouseEvent, registerPage1,
                StageTitles.USER_REGISTER);
    }
}

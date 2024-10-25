package com.bank.guibank.controller.user;

import com.bank.guibank.model.database.user.UserDAO;
import com.bank.guibank.model.user.User;
import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterValidationController {

    @FXML
    public TextField firstname;

    @FXML
    public TextField lastname;

    @FXML
    public Label errorMessage;

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
    public void handleValidationRequest(Event event) throws IOException {
        if (!isValidUser()) {
            errorMessage.setOpacity(1);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/com/bank/guibank/view/user"
                            + "/Registration.fxml"));

            Parent root = fxmlLoader.load();
            ((RegistrationController) fxmlLoader.getController())
                    .setUser(user);
            Scene registerPage2 = new Scene(root);
            StageManagement.switchScene(event, registerPage2,
                    StageTitles.USER_REGISTER);
        }
    }

    private boolean isValidUser() {
        String firstname = this.firstname.getText();
        String lastname = this.lastname.getText();
        UserDAO userDAO = new UserDAO();
        user = userDAO.getUserByFullName(firstname, lastname);
        return user != null && !user.isAccount();
    }
}

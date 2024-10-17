package com.bank.guibank.controller;

import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainViewController {

    @FXML
    public void userBtnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/Login.fxml"));
        Scene userScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, userScene,
                StageTitles.USER_LOGIN);
    }

    @FXML
    public void exitBtnAction(MouseEvent event) {
        StageManagement.closeWindow(event);
    }
}

package com.bank.guibank.controller;

import com.bank.guibank.util.StageManagement;
import com.bank.guibank.util.StageTitles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class MainViewController {

    public void userBtnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/com/bank/guibank/view/user/UserLoginView.fxml"));
        Scene userScene = new Scene(fxmlLoader.load());
        StageManagement.switchScene(mouseEvent, userScene,
                StageTitles.USER_LOGIN);
    }

    public void exitBtnAction(MouseEvent event) {
        StageManagement.closeWindow(event);
    }
}

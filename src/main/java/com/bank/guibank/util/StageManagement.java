package com.bank.guibank.util;


import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManagement {


    public static void switchScene(Event event, Scene scene, String newTitle) {
        Stage stage = (Stage) ((Node) (event.getSource()))
                .getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(newTitle);
        stage.centerOnScreen();
        stage.show();
    }

    public static void closeWindow(Event event) {
        Stage stage = (Stage) ((Node) (event.getSource()))
                .getScene().getWindow();
        stage.close();
    }
}

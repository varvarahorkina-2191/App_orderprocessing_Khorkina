package com.example.app_orderprocessing.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationDialog {

    public static boolean show(String text) {
        Alert window = new Alert(Alert.AlertType.CONFIRMATION);

        window.setTitle("Подтверждение");
        window.setHeaderText(null);
        window.setContentText(text);

        ButtonType button = window.showAndWait().orElse(ButtonType.CANCEL);

        return button == ButtonType.OK;
    }
}
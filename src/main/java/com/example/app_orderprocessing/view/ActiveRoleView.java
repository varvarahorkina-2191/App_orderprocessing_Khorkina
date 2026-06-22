package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ActiveRoleView extends VBox {

    private ComboBox<String> roleComboBox;
    private Button continueButton;
    private Label messageLabel;

    public ActiveRoleView() {
        Label titleLabel = new Label("Выбор активной роли");
        Label roleLabel = new Label("Выберите роль для работы:");

        roleComboBox = new ComboBox<String>();
        roleComboBox.setPromptText("Выберите роль");

        continueButton = new Button("Продолжить");

        messageLabel = new Label();

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().add(titleLabel);
        getChildren().add(roleLabel);
        getChildren().add(roleComboBox);
        getChildren().add(continueButton);
        getChildren().add(messageLabel);
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PersonalAccView extends VBox {

    private TextField loginField;

    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField repeatPasswordField;

    private Button saveButton;
    private Button backButton;

    private Label messageLabel;

    public PersonalAccView() {
        Label title = new Label("Личный кабинет");

        Label loginText = new Label("Логин:");
        loginField = new TextField();
        loginField.setPromptText("Введите логин");

        Label currentPasswordText = new Label("Текущий пароль:");
        currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Введите текущий пароль");

        Label newPasswordText = new Label("Новый пароль:");
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Введите новый пароль");

        Label repeatPasswordText = new Label("Повторите новый пароль:");
        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Повторите новый пароль");

        saveButton = new Button("Сохранить");
        backButton = new Button("Назад");

        saveButton.setPrefWidth(130);
        backButton.setPrefWidth(130);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(saveButton);
        buttons.getChildren().add(backButton);

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().add(title);
        getChildren().add(loginText);
        getChildren().add(loginField);
        getChildren().add(currentPasswordText);
        getChildren().add(currentPasswordField);
        getChildren().add(newPasswordText);
        getChildren().add(newPasswordField);
        getChildren().add(repeatPasswordText);
        getChildren().add(repeatPasswordField);
        getChildren().add(buttons);
        getChildren().add(messageLabel);
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getCurrentPasswordField() {
        return currentPasswordField;
    }

    public PasswordField getNewPasswordField() {
        return newPasswordField;
    }

    public PasswordField getRepeatPasswordField() {
        return repeatPasswordField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
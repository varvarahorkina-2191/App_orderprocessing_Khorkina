package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PersonalAccView extends VBox {

    private TextField loginField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;

    private Button saveButton;
    private Button backButton;

    private Label messageLabel;

    public PersonalAccView() {
        Label titleLabel = new Label("Личный кабинет");

        Label loginLabel = new Label("Логин:");

        loginField = new TextField();
        loginField.setPromptText("Введите новый логин");

        Label passwordLabel = new Label("Новый пароль:");

        passwordField = new PasswordField();
        passwordField.setPromptText("Введите новый пароль");

        Label repeatPasswordLabel =
                new Label("Повторите новый пароль:");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText(
                "Повторите новый пароль"
        );

        saveButton = new Button("Сохранить");
        backButton = new Button("Назад");

        messageLabel = new Label();

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().add(titleLabel);
        getChildren().add(loginLabel);
        getChildren().add(loginField);
        getChildren().add(passwordLabel);
        getChildren().add(passwordField);
        getChildren().add(repeatPasswordLabel);
        getChildren().add(repeatPasswordField);
        getChildren().add(saveButton);
        getChildren().add(backButton);
        getChildren().add(messageLabel);
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
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
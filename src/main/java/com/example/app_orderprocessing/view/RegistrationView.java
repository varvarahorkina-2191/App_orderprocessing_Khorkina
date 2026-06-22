package com.example.app_orderprocessing.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegistrationView extends VBox {

    private TextField loginField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;

    private Button registerButton;
    private Button backButton;

    private Label messageLabel;

    public RegistrationView() {

        Label titleLabel = new Label("Регистрация");

        loginField = new TextField();
        loginField.setPromptText("Введите логин");

        passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Повторите пароль");

        registerButton = new Button("Зарегистрироваться");
        backButton = new Button("Назад");

        messageLabel = new Label();

        setSpacing(10);
        setStyle("-fx-padding: 20;");

        getChildren().add(titleLabel);
        getChildren().add(loginField);
        getChildren().add(passwordField);
        getChildren().add(repeatPasswordField);
        getChildren().add(registerButton);
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

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
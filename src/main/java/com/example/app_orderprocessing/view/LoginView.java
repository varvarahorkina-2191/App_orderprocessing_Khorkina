package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private TextField loginField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registrationButton;
    private Label messageLabel;

    public LoginView() {
        Label titleLabel = new Label("Авторизация");

        loginField = new TextField();
        loginField.setPromptText("Введите логин");

        passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");

        loginButton = new Button("Войти");
        registrationButton = new Button("Регистрация");

        messageLabel = new Label();

        setSpacing(10);
        setPadding(new Insets(20));

        getChildren().add(titleLabel);
        getChildren().add(loginField);
        getChildren().add(passwordField);
        getChildren().add(loginButton);
        getChildren().add(registrationButton);
        getChildren().add(messageLabel);
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegistrationButton() {
        return registrationButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
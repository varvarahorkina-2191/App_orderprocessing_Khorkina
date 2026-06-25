package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationView extends VBox {

    private TextField loginField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;

    private Button registerButton;
    private Button backButton;

    private Label messageLabel;

    public RegistrationView() {
        Label title = new Label("Регистрация");

        Label loginText = new Label("Логин:");
        loginField = new TextField();
        loginField.setPromptText("Введите логин");
        loginField.setPrefWidth(260);

        Label passwordText = new Label("Пароль:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");
        passwordField.setPrefWidth(260);

        Label repeatPasswordText = new Label("Повторите пароль:");
        repeatPasswordField = new PasswordField();
        repeatPasswordField.setPromptText("Повторите пароль");
        repeatPasswordField.setPrefWidth(260);

        registerButton = new Button("Зарегистрироваться");
        backButton = new Button("Назад");

        registerButton.setPrefWidth(170);
        backButton.setPrefWidth(190);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(registerButton);
        buttons.getChildren().add(backButton);
        buttons.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        setSpacing(10);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);

        getChildren().add(title);
        getChildren().add(loginText);
        getChildren().add(loginField);
        getChildren().add(passwordText);
        getChildren().add(passwordField);
        getChildren().add(repeatPasswordText);
        getChildren().add(repeatPasswordField);
        getChildren().add(buttons);
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
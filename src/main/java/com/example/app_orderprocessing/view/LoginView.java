package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private TextField loginField;
    private PasswordField passwordField;

    private Button loginButton;
    private Button registrationButton;

    private Label messageLabel;

    public LoginView() {
        Label title = new Label("Авторизация");

        loginField = new TextField();
        loginField.setPromptText("Введите логин");
        loginField.setPrefWidth(260);

        passwordField = new PasswordField();
        passwordField.setPromptText("Введите пароль");
        passwordField.setPrefWidth(260);

        loginButton = new Button("Войти");
        registrationButton = new Button("Регистрация");

        loginButton.setPrefWidth(125);
        registrationButton.setPrefWidth(125);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(loginButton);
        buttons.getChildren().add(registrationButton);
        buttons.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(280);

        setSpacing(12);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);

        getChildren().add(title);
        getChildren().add(loginField);
        getChildren().add(passwordField);
        getChildren().add(buttons);
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
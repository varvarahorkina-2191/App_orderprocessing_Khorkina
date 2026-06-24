package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.view.ActiveRoleView;
import com.example.app_orderprocessing.view.LoginView;
import com.example.app_orderprocessing.view.RegistrationView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController implements EventHandler<ActionEvent> {

    private LoginView loginView;
    private UserDao userDao;
    private Stage stage;

    public LoginController(LoginView loginView, Stage stage) {
        this.loginView = loginView;
        this.stage = stage;

        userDao = new UserDao();

        loginView.getLoginButton().setOnAction(this);
        loginView.getRegistrationButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == loginView.getLoginButton()) {
            login();
        } else if (source == loginView.getRegistrationButton()) {
            openRegistration();
        }
    }

    private void login() {
        String login = loginView.getLoginField().getText().trim();
        String password = loginView.getPasswordField().getText();

        if (login.isEmpty()) {
            showMessage("Введите логин");
            return;
        }

        if (password.isEmpty()) {
            showMessage("Введите пароль");
            return;
        }

        User user = userDao.findByLogin(login);

        if (user == null) {
            showMessage("Неверный логин или пароль");
            return;
        }

        boolean passwordCorrect = PasswordHasher.check(password, user.getHashPassword());

        if (passwordCorrect == false) {
            showMessage("Неверный логин или пароль");
            return;
        }

        openActiveRole(user);
    }

    private void openActiveRole(User user) {
        ActiveRoleView view = new ActiveRoleView();
        new ActiveRoleController(view, user, stage);

        Scene scene = new Scene(view, 350, 250);

        stage.setTitle("Выбор активной роли");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    private void openRegistration() {
        RegistrationView view = new RegistrationView();
        new RegistrationController(view, stage);

        Scene scene = new Scene(view, 400, 430);

        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    private void showMessage(String text) {
        loginView.getMessageLabel().setText(text);
    }
}
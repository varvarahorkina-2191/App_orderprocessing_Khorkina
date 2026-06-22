package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.view.CustomerView;
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
        if (event.getSource() == loginView.getLoginButton()) {
            login();
        }

        if (event.getSource() == loginView.getRegistrationButton()) {
            openRegistration();
        }
    }

    private void login() {
        String login = loginView.getLoginField().getText();
        String password = loginView.getPasswordField().getText();

        if (login.isEmpty()) {
            loginView.getMessageLabel().setText("Введите логин");
            return;
        }

        if (password.isEmpty()) {
            loginView.getMessageLabel().setText("Введите пароль");
            return;
        }

        User user = userDao.findByLogin(login);

        if (user == null) {
            loginView.getMessageLabel().setText(
                    "Пользователь не найден"
            );
            return;
        }

        boolean passwordCorrect;

        passwordCorrect = PasswordHasher.check(
                password,
                user.getHashPassword()
        );

        if (passwordCorrect == false) {
            loginView.getMessageLabel().setText(
                    "Неверный пароль"
            );
            return;
        }

        openCustomers(user);
    }

    private void openCustomers(User user) {
        CustomerView customerView = new CustomerView();

        new CustomerController(
                customerView,
                user
        );

        Scene scene = new Scene(
                customerView,
                900,
                500
        );

        stage.setTitle("Заказчики");
        stage.setScene(scene);
    }

    private void openRegistration() {
        RegistrationView registrationView =
                new RegistrationView();

        new RegistrationController(
                registrationView
        );

        Scene scene = new Scene(
                registrationView,
                400,
                350
        );

        stage.setTitle("Регистрация");
        stage.setScene(scene);
    }
}
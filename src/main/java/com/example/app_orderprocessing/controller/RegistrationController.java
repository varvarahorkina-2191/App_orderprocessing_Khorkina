package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.util.PasswordValidator;
import com.example.app_orderprocessing.view.LoginView;
import com.example.app_orderprocessing.view.RegistrationView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistrationController implements EventHandler<ActionEvent> {

    private RegistrationView view;
    private UserDao userDao;
    private Stage stage;

    public RegistrationController(RegistrationView view, Stage stage) {
        this.view = view;
        this.stage = stage;

        userDao = new UserDao();

        view.getRegisterButton().setOnAction(this);
        view.getBackButton().setOnAction(this);
        view.getRepeatPasswordField().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getRegisterButton() || source == view.getRepeatPasswordField()) {
            register();
        } else if (source == view.getBackButton()) {
            openLogin();
        }
    }

    private void register() {
        String login = view.getLoginField().getText().trim();
        String password = view.getPasswordField().getText();
        String repeatPassword = view.getRepeatPasswordField().getText();

        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            showMessage("Заполните все поля");
            return;
        }

        if (login.length() < 4) {
            showMessage("Логин должен содержать минимум 4 символа");
            return;
        }

        if (login.contains(" ")) {
            showMessage("Логин не должен содержать пробелы");
            return;
        }

        User existingUser = userDao.findByLogin(login);

        if (existingUser != null) {
            showMessage("Пользователь с таким логином уже существует");
            return;
        }

        if (password.equals(repeatPassword) == false) {
            showMessage("Пароли не совпадают");
            clearPasswordFields();
            return;
        }

        if (PasswordValidator.isValid(password) == false) {
            showMessage("Пароль должен содержать минимум 8 символов, заглавную букву, цифру и специальный символ");
            return;
        }

        String hashPassword = PasswordHasher.hash(password);
        boolean registered = userDao.registerCustomer(login, hashPassword);

        if (registered) {
            showMessage("Регистрация успешно завершена");

            view.getRegisterButton().setDisable(true);
            view.getLoginField().setDisable(true);
            view.getPasswordField().setDisable(true);
            view.getRepeatPasswordField().setDisable(true);
            view.getBackButton().setText("Перейти ко входу");
        } else {
            showMessage("Не удалось зарегистрировать пользователя");
        }
    }

    private void clearPasswordFields() {
        view.getPasswordField().clear();
        view.getRepeatPasswordField().clear();
    }

    private void openLogin() {
        LoginView loginView = new LoginView();
        new LoginController(loginView, stage);

        stage.setTitle("Авторизация");
        stage.setScene(new Scene(loginView, 400, 300));
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
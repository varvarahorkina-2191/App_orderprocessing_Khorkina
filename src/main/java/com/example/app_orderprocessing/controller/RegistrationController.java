package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.util.PasswordValidator;
import com.example.app_orderprocessing.view.RegistrationView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class RegistrationController implements EventHandler<ActionEvent> {

    private RegistrationView registrationView;
    private UserDao userDao;

    public RegistrationController(
            RegistrationView registrationView
    ) {
        this.registrationView = registrationView;

        userDao = new UserDao();

        registrationView.getRegisterButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()
                == registrationView.getRegisterButton()) {

            registerUser();
        }
    }

    private void registerUser() {
        String login =
                registrationView.getLoginField().getText();

        String password =
                registrationView.getPasswordField().getText();

        String repeatPassword =
                registrationView
                        .getRepeatPasswordField()
                        .getText();

        if (login.isEmpty()) {
            registrationView.getMessageLabel().setText(
                    "Введите логин"
            );
            return;
        }

        if (password.isEmpty()) {
            registrationView.getMessageLabel().setText(
                    "Введите пароль"
            );
            return;
        }

        if (repeatPassword.isEmpty()) {
            registrationView.getMessageLabel().setText(
                    "Повторите пароль"
            );
            return;
        }

        if (password.equals(repeatPassword) == false) {
            registrationView.getMessageLabel().setText(
                    "Пароли не совпадают"
            );
            return;
        }

        boolean passwordValid;
        passwordValid = PasswordValidator.isValid(password);

        if (passwordValid == false) {
            registrationView.getMessageLabel().setText(
                    "Пароль должен содержать минимум 8 символов, " +
                            "заглавную букву, цифру и специальный символ"
            );
            return;
        }

        User oldUser = userDao.findByLogin(login);

        if (oldUser != null) {
            registrationView.getMessageLabel().setText(
                    "Такой логин уже существует"
            );
            return;
        }

        String hashPassword;
        hashPassword = PasswordHasher.hash(password);

        int roleId = 2;

        User newUser = new User(
                roleId,
                login,
                hashPassword
        );

        boolean userAdded;
        userAdded = userDao.addUser(newUser);

        if (userAdded == false) {
            registrationView.getMessageLabel().setText(
                    "Ошибка регистрации"
            );
            return;
        }

        User savedUser = userDao.findByLogin(login);

        if (savedUser != null) {
            userDao.addUserRole(
                    savedUser.getId(),
                    roleId
            );
        }

        registrationView.getMessageLabel().setText(
                "Регистрация выполнена"
        );

        registrationView.getLoginField().clear();
        registrationView.getPasswordField().clear();
        registrationView.getRepeatPasswordField().clear();
    }
}
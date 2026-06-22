package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.util.PasswordValidator;
import com.example.app_orderprocessing.view.RegistrationView;

public class RegistrationController {

    private RegistrationView view;
    private UserDao userDao;

    public RegistrationController(RegistrationView view) {
        this.view = view;
        this.userDao = new UserDao();

        view.getRegisterButton().setOnAction(event -> registerUser());
    }

    private void registerUser() {
        String login = view.getLoginField().getText();
        String password = view.getPasswordField().getText();
        String repeatPassword = view.getRepeatPasswordField().getText();

        if (login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            view.getMessageLabel().setText("Заполните все поля");
            return;
        }

        if (!password.equals(repeatPassword)) {
            view.getMessageLabel().setText("Пароли не совпадают");
            return;
        }

        if (!PasswordValidator.isValid(password)) {
            view.getMessageLabel().setText(
                    "Пароль должен содержать 8 символов, " +
                            "заглавную букву, цифру и специальный символ"
            );
            return;
        }

        User oldUser = userDao.findByLogin(login);

        if (oldUser != null) {
            view.getMessageLabel().setText("Такой логин уже существует");
            return;
        }

        String hash = PasswordHasher.hash(password);

        int managerRoleId = 2;

        User newUser = new User(
                managerRoleId,
                login,
                hash
        );

        boolean result = userDao.addUser(newUser);

        if (result) {
            User savedUser = userDao.findByLogin(login);

            if (savedUser != null) {
                userDao.addUserRole(
                        savedUser.getId(),
                        managerRoleId
                );
            }

            view.getMessageLabel().setText(
                    "Регистрация выполнена"
            );

            view.getLoginField().clear();
            view.getPasswordField().clear();
            view.getRepeatPasswordField().clear();
        } else {
            view.getMessageLabel().setText(
                    "Ошибка регистрации"
            );
        }
    }
}
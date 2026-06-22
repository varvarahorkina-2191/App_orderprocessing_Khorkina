package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.util.PasswordValidator;
import com.example.app_orderprocessing.view.CustomerView;
import com.example.app_orderprocessing.view.PersonalAccView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PersonalAccController
        implements EventHandler<ActionEvent> {

    private PersonalAccView personalAccView;
    private UserDao userDao;
    private User user;
    private Stage stage;

    public PersonallAccController(
            PersonalAccView personalAccView,
            User user,
            Stage stage
    ) {
        this.personalAccView = personalAccView;
        this.user = user;
        this.stage = stage;

        userDao = new UserDao();

        personalAccView
                .getLoginField()
                .setText(user.getLogin());

        personalAccView
                .getSaveButton()
                .setOnAction(this);

        personalAccView
                .getBackButton()
                .setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()
                == personalAccView.getSaveButton()) {

            saveUserData();
        }

        if (event.getSource()
                == personalAccView.getBackButton()) {

            openCustomers();
        }
    }

    private void saveUserData() {
        String newLogin =
                personalAccView
                        .getLoginField()
                        .getText();

        String newPassword =
                personalAccView
                        .getPasswordField()
                        .getText();

        String repeatPassword =
                personalAccView
                        .getRepeatPasswordField()
                        .getText();

        if (newLogin.isEmpty()) {
            personalAccView
                    .getMessageLabel()
                    .setText("Введите логин");

            return;
        }

        User foundUser =
                userDao.findByLogin(newLogin);

        if (foundUser != null
                && foundUser.getId() != user.getId()) {

            personalAccView
                    .getMessageLabel()
                    .setText("Такой логин уже существует");

            return;
        }

        if (newPassword.isEmpty() == false
                || repeatPassword.isEmpty() == false) {

            if (newPassword.isEmpty()
                    || repeatPassword.isEmpty()) {

                personalAccView
                        .getMessageLabel()
                        .setText("Заполните оба поля пароля");

                return;
            }

            if (newPassword.equals(repeatPassword) == false) {
                personalAccView
                        .getMessageLabel()
                        .setText("Пароли не совпадают");

                return;
            }

            boolean passwordValid;

            passwordValid =
                    PasswordValidator.isValid(newPassword);

            if (passwordValid == false) {
                personalAccView
                        .getMessageLabel()
                        .setText(
                                "Пароль должен содержать минимум 8 символов, " +
                                        "заглавную букву, цифру и специальный символ"
                        );

                return;
            }
        }

        boolean loginChanged = false;
        boolean passwordChanged = false;

        if (newLogin.equals(user.getLogin()) == false) {
            loginChanged = userDao.updateLogin(
                    user.getId(),
                    newLogin
            );

            if (loginChanged == true) {
                user.setLogin(newLogin);
            }
        }

        if (newPassword.isEmpty() == false) {
            String passwordHash;

            passwordHash =
                    PasswordHasher.hash(newPassword);

            passwordChanged =
                    userDao.updatePassword(
                            user.getId(),
                            passwordHash
                    );

            if (passwordChanged == true) {
                user.setHashPassword(passwordHash);
            }
        }

        if (loginChanged == true
                || passwordChanged == true) {

            personalAccView
                    .getMessageLabel()
                    .setText("Данные изменены");

            personalAccView
                    .getPasswordField()
                    .clear();

            personalAccView
                    .getRepeatPasswordField()
                    .clear();

        } else {
            personalAccView
                    .getMessageLabel()
                    .setText("Изменений нет");
        }
    }

    private void openCustomers() {
        CustomerView customerView =
                new CustomerView();

        new CustomerController(
                customerView,
                user,
                stage
        );

        Scene scene = new Scene(
                customerView,
                900,
                500
        );

        stage.setTitle("Заказчики");
        stage.setScene(scene);
    }
}
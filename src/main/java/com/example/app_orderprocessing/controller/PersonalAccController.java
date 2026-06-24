package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.UserDao;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.PasswordHasher;
import com.example.app_orderprocessing.util.PasswordValidator;
import com.example.app_orderprocessing.view.MainMenuView;
import com.example.app_orderprocessing.view.PersonalAccView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PersonalAccController implements EventHandler<ActionEvent> {

    private PersonalAccView view;
    private UserDao userDao;
    private User user;
    private MainMenuView mainMenuView;

    public PersonalAccController(
            PersonalAccView view,
            User user,
            MainMenuView mainMenuView
    ) {
        this.view = view;
        this.user = user;
        this.mainMenuView = mainMenuView;

        userDao = new UserDao();

        view.getLoginField().setText(user.getLogin());
        view.getSaveButton().setOnAction(this);
        view.getBackButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getSaveButton()) {
            saveUserData();
        } else if (source == view.getBackButton()) {
            mainMenuView.showWelcome();
        }
    }

    private void saveUserData() {
        String newLogin = view.getLoginField().getText().trim();
        String currentPassword = view.getCurrentPasswordField().getText();
        String newPassword = view.getNewPasswordField().getText();
        String repeatPassword = view.getRepeatPasswordField().getText();

        if (newLogin.isEmpty()) {
            showMessage("Введите логин");
            return;
        }

        if (newLogin.length() < 4) {
            showMessage("Логин должен содержать минимум 4 символа");
            return;
        }

        if (newLogin.contains(" ")) {
            showMessage("Логин не должен содержать пробелы");
            return;
        }

        User foundUser = userDao.findByLogin(newLogin);

        if (foundUser != null && foundUser.getId() != user.getId()) {
            showMessage("Такой логин уже существует");
            return;
        }

        boolean changeLogin = newLogin.equals(user.getLogin()) == false;
        boolean changePassword = currentPassword.isEmpty() == false
                || newPassword.isEmpty() == false
                || repeatPassword.isEmpty() == false;

        if (changePassword && checkPasswordData(
                currentPassword,
                newPassword,
                repeatPassword
        ) == false) {
            return;
        }

        if (changeLogin == false && changePassword == false) {
            showMessage("Изменений нет");
            return;
        }

        boolean loginChanged = true;
        boolean passwordChanged = true;

        if (changeLogin) {
            loginChanged = userDao.updateLogin(user.getId(), newLogin);
        }

        if (changePassword) {
            String hash = PasswordHasher.hash(newPassword);
            passwordChanged = userDao.updatePassword(user.getId(), hash);

            if (passwordChanged) {
                user.setHashPassword(hash);
            }
        }

        if (loginChanged && passwordChanged) {
            if (changeLogin) {
                user.setLogin(newLogin);
                mainMenuView.getUserLabel().setText(
                        "Пользователь: " + user.getLogin()
                );
            }

            clearPasswordFields();
            showMessage("Данные успешно изменены");
        } else {
            showMessage("Не удалось изменить данные");
        }
    }

    private boolean checkPasswordData(
            String currentPassword,
            String newPassword,
            String repeatPassword
    ) {
        if (currentPassword.isEmpty()
                || newPassword.isEmpty()
                || repeatPassword.isEmpty()) {

            showMessage("Заполните все поля пароля");
            return false;
        }

        boolean passwordCorrect;

        try {
            passwordCorrect = PasswordHasher.check(
                    currentPassword,
                    user.getHashPassword()
            );
        } catch (IllegalArgumentException e) {
            passwordCorrect = false;
        }

        if (passwordCorrect == false) {
            showMessage("Текущий пароль указан неверно");
            return false;
        }

        if (newPassword.equals(repeatPassword) == false) {
            showMessage("Новые пароли не совпадают");
            return false;
        }

        if (PasswordValidator.isValid(newPassword) == false) {
            showMessage(
                    "Пароль должен содержать минимум 8 символов, "
                            + "заглавную букву, цифру и специальный символ"
            );
            return false;
        }

        if (PasswordHasher.check(newPassword, user.getHashPassword())) {
            showMessage("Новый пароль совпадает с текущим");
            return false;
        }

        return true;
    }

    private void clearPasswordFields() {
        view.getCurrentPasswordField().clear();
        view.getNewPasswordField().clear();
        view.getRepeatPasswordField().clear();
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
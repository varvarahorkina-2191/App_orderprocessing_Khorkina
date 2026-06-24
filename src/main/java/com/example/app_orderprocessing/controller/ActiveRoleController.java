package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.ActiveRoleView;
import com.example.app_orderprocessing.view.MainMenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ActiveRoleController implements EventHandler<ActionEvent> {

    private ActiveRoleView activeRoleView;
    private RoleDao roleDao;
    private User user;
    private Stage stage;

    private ArrayList<Role> roles;

    public ActiveRoleController(ActiveRoleView activeRoleView, User user, Stage stage) {
        this.activeRoleView = activeRoleView;
        this.user = user;
        this.stage = stage;

        roleDao = new RoleDao();

        loadRoles();

        activeRoleView.getContinueButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == activeRoleView.getContinueButton()) {
            selectRole();
        }
    }

    private void loadRoles() {
        roles = roleDao.getUserRoles(user.getId());

        activeRoleView.getRoleComboBox().getItems().clear();

        int i = 0;

        while (i < roles.size()) {
            Role role = roles.get(i);

            activeRoleView.getRoleComboBox().getItems().add(role.getRoleName());

            i++;
        }

        if (roles.isEmpty()) {
            activeRoleView.getMessageLabel().setText("Пользователю не назначены роли");

            activeRoleView.getContinueButton().setDisable(true);

            return;
        }

        if (roles.size() == 1) {
            Role role = roles.get(0);

            activeRoleView.getRoleComboBox().setValue(role.getRoleName());
        }
    }

    private void selectRole() {
        String roleName = activeRoleView.getRoleComboBox().getValue();

        if (roleName == null) {
            activeRoleView.getMessageLabel().setText("Выберите роль");
            return;
        }

        Role selectedRole = findRoleByName(roleName);

        if (selectedRole == null) {
            activeRoleView.getMessageLabel().setText("Роль не найдена");
            return;
        }

        boolean updated = roleDao.updateActiveRole(
                user.getId(),
                selectedRole.getId()
        );

        if (!updated) {
            activeRoleView.getMessageLabel().setText("Не удалось выбрать роль");

            return;
        }

        user.setActiveRoleId(selectedRole.getId());

        openMainMenu();
    }

    private Role findRoleByName(String roleName) {
        int i = 0;

        while (i < roles.size()) {
            Role role = roles.get(i);

            if (role.getRoleName().equals(roleName)) {
                return role;
            }

            i++;
        }

        return null;
    }

    private void openMainMenu() {
        MainMenuView mainMenuView = new MainMenuView();

        new MainMenuController(mainMenuView, user, stage);

        Scene scene = new Scene(mainMenuView, 1400, 800);

        stage.setTitle("Главное меню");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.centerOnScreen();
    }
}
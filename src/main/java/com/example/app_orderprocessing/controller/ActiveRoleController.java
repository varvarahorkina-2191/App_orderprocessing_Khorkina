package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.ActiveRoleView;
import com.example.app_orderprocessing.view.CustomerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ActiveRoleController
        implements EventHandler<ActionEvent> {

    private ActiveRoleView activeRoleView;
    private RoleDao roleDao;
    private User user;
    private Stage stage;

    private ArrayList<Role> roles;

    public ActiveRoleController(
            ActiveRoleView activeRoleView,
            User user,
            Stage stage
    ) {
        this.activeRoleView = activeRoleView;
        this.user = user;
        this.stage = stage;

        roleDao = new RoleDao();

        loadRoles();

        activeRoleView
                .getContinueButton()
                .setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource()
                == activeRoleView.getContinueButton()) {

            selectRole();
        }
    }

    private void loadRoles() {
        roles = roleDao.getUserRoles(
                user.getId()
        );

        int i = 0;

        while (i < roles.size()) {
            Role role = roles.get(i);

            activeRoleView
                    .getRoleComboBox()
                    .getItems()
                    .add(role.getRoleName());

            i++;
        }

        if (roles.isEmpty()) {
            activeRoleView
                    .getMessageLabel()
                    .setText(
                            "Пользователю не назначены роли"
                    );

            activeRoleView
                    .getContinueButton()
                    .setDisable(true);
        }
    }

    private void selectRole() {
        String roleName;

        roleName = activeRoleView
                .getRoleComboBox()
                .getValue();

        if (roleName == null) {
            activeRoleView
                    .getMessageLabel()
                    .setText("Выберите роль");

            return;
        }

        Role selectedRole = null;

        int i = 0;

        while (i < roles.size()) {
            Role role = roles.get(i);

            if (role.getRoleName().equals(roleName)) {
                selectedRole = role;
            }

            i++;
        }

        if (selectedRole == null) {
            activeRoleView
                    .getMessageLabel()
                    .setText("Роль не найдена");

            return;
        }

        boolean updated;

        updated = roleDao.updateActiveRole(
                user.getId(),
                selectedRole.getId()
        );

        if (updated == false) {
            activeRoleView
                    .getMessageLabel()
                    .setText(
                            "Не удалось выбрать роль"
                    );

            return;
        }

        user.setActiveRoleId(
                selectedRole.getId()
        );

        openCustomers();
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
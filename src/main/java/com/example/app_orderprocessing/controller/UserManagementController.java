package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.dao.UserManagementDao;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.model.UserAccess;
import com.example.app_orderprocessing.util.ConfirmationDialog;
import com.example.app_orderprocessing.view.UserManagementView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class UserManagementController implements EventHandler<ActionEvent> {

    private UserManagementView view;
    private UserManagementDao userManagementDao;
    private RoleDao roleDao;
    private User currentUser;

    private UserAccess selectedUser;
    private ArrayList<Role> allRoles;
    private ArrayList<Role> selectedUserRoles;

    public UserManagementController(UserManagementView view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;

        userManagementDao = new UserManagementDao();
        roleDao = new RoleDao();

        loadAllRoles();
        loadUsers();
        connectEvents();
    }

    private void connectEvents() {
        view.getAddRoleButton().setOnAction(this);
        view.getRemoveRoleButton().setOnAction(this);
        view.getDeleteUserButton().setOnAction(this);
        view.getRefreshButton().setOnAction(this);

        view.getUserTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<UserAccess>() {
                    @Override
                    public void changed(ObservableValue<? extends UserAccess> value,
                                        UserAccess oldUser,
                                        UserAccess newUser) {
                        selectUser(newUser);
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddRoleButton()) {
            addRole();
        } else if (source == view.getRemoveRoleButton()) {
            removeRole();
        } else if (source == view.getDeleteUserButton()) {
            deleteUser();
        } else if (source == view.getRefreshButton()) {
            refreshTable();
        }
    }

    private void loadAllRoles() {
        allRoles = roleDao.getAllRoles();
        view.getRoleComboBox().getItems().clear();

        for (Role role : allRoles) {
            view.getRoleComboBox().getItems().add(role.getRoleName());
        }
    }

    private void loadUsers() {
        view.getUserTable().getItems().setAll(userManagementDao.getAllUsers());
    }

    private void selectUser(UserAccess user) {
        selectedUser = user;
        view.getAssignedRolesList().getItems().clear();
        view.getRoleComboBox().setValue(null);

        if (selectedUser == null) {
            selectedUserRoles = null;
            view.getSelectedUserLabel().setText("Пользователь не выбран");
            return;
        }

        String text = "Выбран пользователь: " + selectedUser.getLogin()
                + ". Активная роль: " + selectedUser.getActiveRoleName();

        view.getSelectedUserLabel().setText(text);
        loadSelectedUserRoles();
    }

    private void loadSelectedUserRoles() {
        selectedUserRoles = roleDao.getUserRoles(selectedUser.getId());
        view.getAssignedRolesList().getItems().clear();

        for (Role role : selectedUserRoles) {
            String text = role.getRoleName();

            if (role.getId() == selectedUser.getActiveRoleId()) {
                text += " — активная";
            }

            view.getAssignedRolesList().getItems().add(text);
        }
    }

    private void addRole() {
        if (checkSelectedUser() == false) {
            return;
        }

        String roleName = view.getRoleComboBox().getValue();

        if (roleName == null) {
            showMessage("Выберите роль для добавления");
            return;
        }

        Role role = findRoleByName(roleName);

        if (role == null) {
            showMessage("Роль не найдена");
            return;
        }

        if (userHasRole(role.getId())) {
            showMessage("Эта роль уже назначена пользователю");
            return;
        }

        String text = "Добавить роль " + roleName
                + " пользователю " + selectedUser.getLogin() + "?";

        if (ConfirmationDialog.show(text) == false) {
            return;
        }

        boolean added = userManagementDao.addRoleToUser(
                selectedUser.getId(),
                role.getId()
        );

        if (added) {
            showMessage("Роль добавлена пользователю");
            view.getRoleComboBox().setValue(null);
            loadSelectedUserRoles();
        } else {
            showMessage("Не удалось добавить роль");
        }
    }

    private void removeRole() {
        if (checkSelectedUser() == false) {
            return;
        }

        String text = view.getAssignedRolesList().getSelectionModel().getSelectedItem();

        if (text == null) {
            showMessage("Выберите назначенную роль");
            return;
        }

        Role role = findRoleFromListText(text);

        if (role == null) {
            showMessage("Роль не найдена");
            return;
        }

        if (selectedUserRoles.size() <= 1) {
            showMessage("Нельзя удалить последнюю роль пользователя");
            return;
        }

        String question = "Удалить роль " + role.getRoleName()
                + " у пользователя " + selectedUser.getLogin() + "?";

        if (ConfirmationDialog.show(question) == false) {
            return;
        }

        int userId = selectedUser.getId();

        boolean removed = userManagementDao.removeRoleFromUser(
                userId,
                role.getId(),
                selectedUser.getActiveRoleId()
        );

        if (removed) {
            showMessage("Роль удалена у пользователя");
            loadUsers();
            restoreUserSelection(userId);
        } else {
            showMessage("Не удалось удалить роль");
        }
    }

    private void deleteUser() {
        if (selectedUser == null) {
            showMessage("Выберите пользователя");
            return;
        }

        if (selectedUser.getId() == currentUser.getId()) {
            showMessage("Нельзя удалить свою учетную запись");
            return;
        }

        String text = "Удалить пользователя " + selectedUser.getLogin() + "?";

        if (ConfirmationDialog.show(text) == false) {
            return;
        }

        boolean deleted = userManagementDao.deleteUser(selectedUser.getId());

        if (deleted) {
            showMessage("Пользователь удалён");
            clearSelection();
            loadUsers();
        } else {
            showMessage("Не удалось удалить пользователя");
        }
    }

    private boolean checkSelectedUser() {
        if (selectedUser == null) {
            showMessage("Выберите пользователя");
            return false;
        }

        if (selectedUser.getId() == currentUser.getId()) {
            showMessage("Нельзя изменять собственные роли");
            return false;
        }

        return true;
    }

    private Role findRoleByName(String name) {
        for (Role role : allRoles) {
            if (role.getRoleName().equals(name)) {
                return role;
            }
        }

        return null;
    }

    private Role findRoleFromListText(String text) {
        if (selectedUserRoles == null) {
            return null;
        }

        for (Role role : selectedUserRoles) {
            if (text.startsWith(role.getRoleName())) {
                return role;
            }
        }

        return null;
    }

    private boolean userHasRole(int roleId) {
        if (selectedUserRoles == null) {
            return false;
        }

        for (Role role : selectedUserRoles) {
            if (role.getId() == roleId) {
                return true;
            }
        }

        return false;
    }

    private void restoreUserSelection(int userId) {
        for (UserAccess user : view.getUserTable().getItems()) {
            if (user.getId() == userId) {
                view.getUserTable().getSelectionModel().select(user);
                return;
            }
        }

        clearSelection();
    }

    private void refreshTable() {
        clearSelection();
        loadAllRoles();
        loadUsers();
        showMessage("Список пользователей обновлён");
    }

    private void clearSelection() {
        selectedUser = null;
        selectedUserRoles = null;

        view.getUserTable().getSelectionModel().clearSelection();
        view.getAssignedRolesList().getItems().clear();
        view.getRoleComboBox().setValue(null);
        view.getSelectedUserLabel().setText("Пользователь не выбран");
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
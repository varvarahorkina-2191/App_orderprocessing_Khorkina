package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.UserAccess;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserManagementView extends VBox {

    private TableView<UserAccess> userTable;
    private ListView<String> assignedRolesList;
    private ComboBox<String> roleComboBox;

    private Button addRoleButton;
    private Button removeRoleButton;
    private Button deleteUserButton;
    private Button refreshButton;

    private Label selectedUserLabel;
    private Label messageLabel;

    public UserManagementView() {
        Label title = new Label("Управление пользователями");

        createTable();
        createRoles();
        createButtons();

        selectedUserLabel = new Label("Пользователь не выбран");

        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(800);

        VBox assignedRolesBox = new VBox(8);
        assignedRolesBox.getChildren().add(
                new Label("Назначенные роли:")
        );
        assignedRolesBox.getChildren().add(assignedRolesList);

        VBox newRoleBox = new VBox(8);
        newRoleBox.getChildren().add(
                new Label("Роль для добавления:")
        );
        newRoleBox.getChildren().add(roleComboBox);

        HBox roles = new HBox(20);
        roles.setAlignment(Pos.CENTER);
        roles.getChildren().add(assignedRolesBox);
        roles.getChildren().add(newRoleBox);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(addRoleButton);
        buttons.getChildren().add(removeRoleButton);
        buttons.getChildren().add(deleteUserButton);
        buttons.getChildren().add(refreshButton);

        setSpacing(14);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);

        getChildren().add(title);
        getChildren().add(userTable);
        getChildren().add(selectedUserLabel);
        getChildren().add(roles);
        getChildren().add(buttons);
        getChildren().add(messageLabel);
    }

    private void createTable() {
        userTable = new TableView<UserAccess>();

        TableColumn<UserAccess, Integer> idColumn =
                new TableColumn<UserAccess, Integer>("ID");

        TableColumn<UserAccess, String> loginColumn =
                new TableColumn<UserAccess, String>("Логин");

        TableColumn<UserAccess, String> roleColumn =
                new TableColumn<UserAccess, String>("Активная роль");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<UserAccess, Integer>("id")
        );

        loginColumn.setCellValueFactory(
                new PropertyValueFactory<UserAccess, String>("login")
        );

        roleColumn.setCellValueFactory(
                new PropertyValueFactory<UserAccess, String>(
                        "activeRoleName"
                )
        );

        idColumn.setPrefWidth(100);
        loginColumn.setPrefWidth(300);
        roleColumn.setPrefWidth(250);

        userTable.getColumns().add(idColumn);
        userTable.getColumns().add(loginColumn);
        userTable.getColumns().add(roleColumn);

        userTable.setPrefHeight(300);
        userTable.setPrefWidth(850);
    }

    private void createRoles() {
        assignedRolesList = new ListView<String>();
        assignedRolesList.setPrefWidth(260);
        assignedRolesList.setPrefHeight(130);

        roleComboBox = new ComboBox<String>();
        roleComboBox.setPromptText("Выберите роль");
        roleComboBox.setPrefWidth(260);
    }

    private void createButtons() {
        addRoleButton = new Button("Добавить роль");
        removeRoleButton = new Button("Удалить роль");
        deleteUserButton = new Button("Удалить пользователя");
        refreshButton = new Button("Обновить");

        addRoleButton.setPrefWidth(160);
        removeRoleButton.setPrefWidth(160);
        deleteUserButton.setPrefWidth(190);
        refreshButton.setPrefWidth(130);
    }

    public TableView<UserAccess> getUserTable() {
        return userTable;
    }

    public ListView<String> getAssignedRolesList() {
        return assignedRolesList;
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public Button getAddRoleButton() {
        return addRoleButton;
    }

    public Button getRemoveRoleButton() {
        return removeRoleButton;
    }

    public Button getDeleteUserButton() {
        return deleteUserButton;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }

    public Label getSelectedUserLabel() {
        return selectedUserLabel;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
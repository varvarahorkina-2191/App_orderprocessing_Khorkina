package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.CustomerView;
import com.example.app_orderprocessing.view.DealElementView;
import com.example.app_orderprocessing.view.DeliveryMethodView;
import com.example.app_orderprocessing.view.DocumentView;
import com.example.app_orderprocessing.view.ItemDeliveryView;
import com.example.app_orderprocessing.view.ItemView;
import com.example.app_orderprocessing.view.LoginView;
import com.example.app_orderprocessing.view.MainMenuView;
import com.example.app_orderprocessing.view.PersonalAccView;
import com.example.app_orderprocessing.view.UserManagementView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController implements EventHandler<ActionEvent> {

    private MainMenuView view;
    private User user;
    private Stage mainStage;
    private String activeRoleName;

    public MainMenuController(MainMenuView view, User user, Stage mainStage) {
        this.view = view;
        this.user = user;
        this.mainStage = mainStage;

        activeRoleName = findActiveRoleName();

        showUserInformation();
        configureAccess();
        connectButtons();
    }

    private void connectButtons() {
        view.getUsersButton().setOnAction(this);
        view.getCustomersButton().setOnAction(this);
        view.getItemsButton().setOnAction(this);
        view.getDeliveryMethodsButton().setOnAction(this);
        view.getItemDeliveryButton().setOnAction(this);
        view.getDocumentsButton().setOnAction(this);
        view.getDealElementsButton().setOnAction(this);
        view.getPersonalAccountButton().setOnAction(this);
        view.getExitButton().setOnAction(this);
    }

    private String findActiveRoleName() {
        RoleDao roleDao = new RoleDao();

        Role admin = roleDao.findByName("ADMIN");
        Role manager = roleDao.findByName("MANAGER");
        Role customer = roleDao.findByName("CUSTOMER");

        if (admin != null && user.getActiveRoleId() == admin.getId()) {
            return "ADMIN";
        }

        if (manager != null && user.getActiveRoleId() == manager.getId()) {
            return "MANAGER";
        }

        if (customer != null && user.getActiveRoleId() == customer.getId()) {
            return "CUSTOMER";
        }

        return "Неизвестная роль";
    }

    private void showUserInformation() {
        view.getUserLabel().setText("Пользователь: " + user.getLogin());
        view.getRoleLabel().setText("Активная роль: " + activeRoleName);
    }

    private void configureAccess() {
        if (isAdmin() == false) {
            hideButton(view.getUsersButton());
        }

        if (isCustomer()) {
            hideButton(view.getCustomersButton());
            hideButton(view.getDocumentsButton());
            hideButton(view.getDealElementsButton());
        }
    }

    private boolean isAdmin() {
        return activeRoleName.equals("ADMIN");
    }

    private boolean isCustomer() {
        return activeRoleName.equals("CUSTOMER");
    }

    private void hideButton(Button button) {
        button.setVisible(false);
        button.setManaged(false);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getUsersButton()) {
            openUsers();
        }
        else if (source == view.getCustomersButton()) {
            openCustomers();
        }
        else if (source == view.getItemsButton()) {
            openItems();
        }
        else if (source == view.getDeliveryMethodsButton()) {
            openDeliveryMethods();
        }
        else if (source == view.getItemDeliveryButton()) {
            openItemDelivery();
        }
        else if (source == view.getDocumentsButton()) {
            openDocuments();
        }
        else if (source == view.getDealElementsButton()) {
            openDealElements();
        }
        else if (source == view.getPersonalAccountButton()) {
            openPersonalAccount();
        }
        else if (source == view.getExitButton()) {
            exitFromAccount();
        }
    }

    private void openUsers() {
        if (isAdmin() == false) {
            return;
        }

        UserManagementView content = new UserManagementView();
        new UserManagementController(content, user);
        view.showContent("Управление пользователями", content);
    }

    private void openCustomers() {
        if (isCustomer()) {
            return;
        }

        CustomerView content = new CustomerView();
        new CustomerController(content, user);
        view.showContent("Заказчики", content);
    }

    private void openItems() {
        ItemView content = new ItemView();
        new ItemController(content, user);
        view.showContent("Товары", content);
    }

    private void openDeliveryMethods() {
        DeliveryMethodView content = new DeliveryMethodView();
        new DeliveryMethodController(content, user);
        view.showContent("Способы доставки", content);
    }

    private void openItemDelivery() {
        ItemDeliveryView content = new ItemDeliveryView();
        new ItemDeliveryController(content, user);
        view.showContent("Товары и способы доставки", content);
    }

    private void openDocuments() {
        if (isCustomer()) {
            return;
        }

        DocumentView content = new DocumentView();
        new DocumentController(content, user);
        view.showContent("Документы", content);
    }

    private void openDealElements() {
        if (isCustomer()) {
            return;
        }

        DealElementView content = new DealElementView();
        new DealElementController(content, user);
        view.showContent("Элементы сделок", content);
    }

    private void openPersonalAccount() {
        PersonalAccView content = new PersonalAccView();
        new PersonalAccController(content, user, view);
        view.showContent("Личный кабинет", content);
    }

    private void exitFromAccount() {
        LoginView loginView = new LoginView();
        new LoginController(loginView, mainStage);

        mainStage.setTitle("Авторизация");
        mainStage.setScene(new Scene(loginView, 400, 300));
        mainStage.setResizable(false);
        mainStage.centerOnScreen();
    }

    public void updateUserInformation() {
        showUserInformation();
    }
}
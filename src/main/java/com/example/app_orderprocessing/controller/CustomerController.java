package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Customer;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.ConfirmationDialog;
import com.example.app_orderprocessing.view.CustomerView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CustomerController implements EventHandler<ActionEvent> {

    private CustomerDao customerDao;
    private RoleDao roleDao;
    private CustomerView view;
    private User user;

    private Customer selectedCustomer;

    public CustomerController(CustomerView view, User user) {
        this.view = view;
        this.user = user;

        customerDao = new CustomerDao();
        roleDao = new RoleDao();

        loadCustomers();
        configureAccess();
        connectEvents();
    }

    private void connectEvents() {
        view.getAddButton().setOnAction(this);
        view.getEditButton().setOnAction(this);
        view.getDeleteButton().setOnAction(this);
        view.getSearchButton().setOnAction(this);
        view.getResetSearchButton().setOnAction(this);
        view.getSearchField().setOnAction(this);

        view.getCustomerTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Customer>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Customer> observable,
                            Customer oldValue,
                            Customer newValue
                    ) {
                        selectCustomer(newValue);
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddButton()) {
            addCustomer();
        } else if (source == view.getEditButton()) {
            updateCustomer();
        } else if (source == view.getDeleteButton()) {
            deleteCustomer();
        } else if (source == view.getSearchButton() || source == view.getSearchField()) {
            searchCustomers();
        } else if (source == view.getResetSearchButton()) {
            resetSearch();
        }
    }

    private void configureAccess() {
        if (isManager()) {
            view.getDeleteButton().setDisable(true);
        }

        if (isCustomer()) {
            view.getAddButton().setDisable(true);
            view.getEditButton().setDisable(true);
            view.getDeleteButton().setDisable(true);

            view.getNameField().setDisable(true);
            view.getAddressField().setDisable(true);
            view.getPhoneField().setDisable(true);
            view.getContactField().setDisable(true);

            showMessage("У вас нет доступа к управлению заказчиками");
        }
    }

    private boolean isManager() {
        Role role = roleDao.findByName("MANAGER");

        if (role != null && user.getActiveRoleId() == role.getId()) {
            return true;
        }

        return false;
    }

    private boolean isCustomer() {
        Role role = roleDao.findByName("CUSTOMER");

        if (role != null && user.getActiveRoleId() == role.getId()) {
            return true;
        }

        return false;
    }

    public void loadCustomers() {
        view.getCustomerTable().getItems().setAll(customerDao.getAllCustomers());
    }

    private void searchCustomers() {
        String searchText = view.getSearchField().getText().trim();

        clearSelectedCustomer();

        if (searchText.isEmpty()) {
            loadCustomers();
            showMessage("Показаны все заказчики");
            return;
        }

        view.getCustomerTable().getItems().setAll(customerDao.searchCustomers(searchText));

        int count = view.getCustomerTable().getItems().size();

        if (count == 0) {
            showMessage("По вашему запросу заказчики не найдены");
        } else {
            showMessage("Найдено заказчиков: " + count);
        }
    }

    private void resetSearch() {
        view.getSearchField().clear();
        clearSelectedCustomer();
        loadCustomers();

        showMessage("Поиск сброшен");
    }

    private void selectCustomer(Customer customer) {
        selectedCustomer = customer;

        if (customer == null) {
            return;
        }

        if (isCustomer()) {
            return;
        }

        view.getNameField().setText(customer.getCustomerName());
        view.getAddressField().setText(customer.getAddress());
        view.getPhoneField().setText(customer.getPhoneNumber());
        view.getContactField().setText(customer.getContactPerson());
    }

    private void addCustomer() {
        if (isCustomer()) {
            showMessage("У вас нет права на добавление заказчиков");
            return;
        }

        Customer customer = readCustomer();

        if (customer == null) {
            return;
        }

        boolean added = customerDao.addCustomer(customer);

        if (added == true) {
            showMessage("Заказчик добавлен");
            clearFields();
            resetSearchAfterChange();
        } else {
            showMessage(
                    "Не удалось добавить заказчика. "
                            + "Возможно, номер телефона уже используется"
            );
        }
    }

    private void updateCustomer() {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение заказчиков");
            return;
        }

        if (selectedCustomer == null) {
            showMessage("Выберите заказчика");
            return;
        }

        Customer enteredCustomer = readCustomer();

        if (enteredCustomer == null) {
            return;
        }

        selectedCustomer.setCustomerName(enteredCustomer.getCustomerName());
        selectedCustomer.setAddress(enteredCustomer.getAddress());
        selectedCustomer.setPhoneNumber(enteredCustomer.getPhoneNumber());
        selectedCustomer.setContactPerson(enteredCustomer.getContactPerson());

        boolean updated = customerDao.updateCustomer(selectedCustomer);

        if (updated == true) {
            showMessage("Данные заказчика изменены");
            clearFields();
            resetSearchAfterChange();
        } else {
            showMessage(
                    "Не удалось изменить заказчика. "
                            + "Возможно, номер телефона уже используется"
            );
        }
    }

    private void deleteCustomer() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        if (selectedCustomer == null) {
            showMessage("Выберите заказчика");
            return;
        }

        String text = "Удалить заказчика «"
                + selectedCustomer.getCustomerName()
                + "»?";

        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = customerDao.deleteCustomer(selectedCustomer.getId());

        if (deleted == true) {
            showMessage("Заказчик удалён");
            clearFields();
            resetSearchAfterChange();
        } else {
            showMessage(
                    "Не удалось удалить заказчика. "
                            + "Возможно, у него есть документы"
            );
        }
    }

    private Customer readCustomer() {
        String name = view.getNameField().getText().trim();
        String address = view.getAddressField().getText().trim();
        String phone = view.getPhoneField().getText().trim();
        String contact = view.getContactField().getText().trim();

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || contact.isEmpty()) {
            showMessage("Заполните все поля");
            return null;
        }

        if (isPhoneValid(phone) == false) {
            showMessage("Введите корректный номер телефона");
            return null;
        }

        Customer customer = new Customer(name, address, phone, contact);

        return customer;
    }

    private boolean isPhoneValid(String phone) {
        String regex = "^\\+?[0-9()\\-\\s]{10,20}$";
        boolean result = phone.matches(regex);

        return result;
    }

    private void resetSearchAfterChange() {
        view.getSearchField().clear();
        loadCustomers();
    }

    private void clearSelectedCustomer() {
        selectedCustomer = null;

        view.getCustomerTable().getSelectionModel().clearSelection();

        view.getNameField().clear();
        view.getAddressField().clear();
        view.getPhoneField().clear();
        view.getContactField().clear();
    }

    private void clearFields() {
        clearSelectedCustomer();
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
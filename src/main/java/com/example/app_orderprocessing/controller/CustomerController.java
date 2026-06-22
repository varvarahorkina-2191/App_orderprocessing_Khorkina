package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.model.Customer;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.CustomerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CustomerController {

    private CustomerDao customerDao;
    private CustomerView customerView;
    private User user;

    public CustomerController(
            CustomerView customerView,
            User user
    ) {
        this.customerView = customerView;
        this.user = user;

        customerDao = new CustomerDao();

        loadCustomers();
        checkRole();

        customerView.getAddButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addCustomer();
                    }
                }
        );

        customerView.getEditButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateCustomer();
                    }
                }
        );

        customerView.getDeleteButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteCustomer();
                    }
                }
        );

        customerView.getCustomerTable().setOnMouseClicked(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        fillFields();
                    }
                }
        );
    }

    private void checkRole() {
        int roleId = user.getActiveRoleId();

        if (roleId == 2) {
            customerView.getDeleteButton().setDisable(true);
        }
    }

    public void loadCustomers() {
        customerView.getCustomerTable().getItems().setAll(
                customerDao.getAllCustomers()
        );
    }

    private void addCustomer() {
        String name = customerView.getNameField().getText();
        String address = customerView.getAddressField().getText();
        String phone = customerView.getPhoneField().getText();
        String contact = customerView.getContactField().getText();

        if (name.isEmpty()
                || address.isEmpty()
                || phone.isEmpty()
                || contact.isEmpty()) {

            customerView.getMessageLabel().setText(
                    "Заполните все поля"
            );

            return;
        }

        Customer customer = new Customer(
                name,
                address,
                phone,
                contact
        );

        boolean added;
        added = customerDao.addCustomer(customer);

        if (added == true) {
            customerView.getMessageLabel().setText(
                    "Заказчик добавлен"
            );

            clearFields();
            loadCustomers();
        } else {
            customerView.getMessageLabel().setText(
                    "Не удалось добавить заказчика"
            );
        }
    }

    private void updateCustomer() {
        Customer selectedCustomer;

        selectedCustomer = customerView
                .getCustomerTable()
                .getSelectionModel()
                .getSelectedItem();

        if (selectedCustomer == null) {
            customerView.getMessageLabel().setText(
                    "Выберите заказчика"
            );
            return;
        }

        String name = customerView.getNameField().getText();
        String address = customerView.getAddressField().getText();
        String phone = customerView.getPhoneField().getText();
        String contact = customerView.getContactField().getText();

        if (name.isEmpty()
                || address.isEmpty()
                || phone.isEmpty()
                || contact.isEmpty()) {

            customerView.getMessageLabel().setText(
                    "Заполните все поля"
            );
            return;
        }

        selectedCustomer.setCustomerName(name);
        selectedCustomer.setAddress(address);
        selectedCustomer.setPhoneNumber(phone);
        selectedCustomer.setContactPerson(contact);

        boolean updated;
        updated = customerDao.updateCustomer(
                selectedCustomer
        );

        if (updated == true) {
            customerView.getMessageLabel().setText(
                    "Данные заказчика изменены"
            );

            clearFields();
            loadCustomers();
        } else {
            customerView.getMessageLabel().setText(
                    "Не удалось изменить заказчика"
            );
        }
    }

    private void deleteCustomer() {
        if (user.getActiveRoleId() == 2) {
            customerView.getMessageLabel().setText(
                    "У вас нет права на удаление"
            );
            return;
        }

        Customer selectedCustomer;

        selectedCustomer = customerView
                .getCustomerTable()
                .getSelectionModel()
                .getSelectedItem();

        if (selectedCustomer == null) {
            customerView.getMessageLabel().setText(
                    "Выберите заказчика"
            );
            return;
        }

        boolean deleted;

        deleted = customerDao.deleteCustomer(
                selectedCustomer.getId()
        );

        if (deleted == true) {
            customerView.getMessageLabel().setText(
                    "Заказчик удалён"
            );

            clearFields();
            loadCustomers();
        } else {
            customerView.getMessageLabel().setText(
                    "Не удалось удалить заказчика"
            );
        }
    }

    private void fillFields() {
        Customer selectedCustomer;

        selectedCustomer = customerView
                .getCustomerTable()
                .getSelectionModel()
                .getSelectedItem();

        if (selectedCustomer != null) {
            customerView.getNameField().setText(
                    selectedCustomer.getCustomerName()
            );

            customerView.getAddressField().setText(
                    selectedCustomer.getAddress()
            );

            customerView.getPhoneField().setText(
                    selectedCustomer.getPhoneNumber()
            );

            customerView.getContactField().setText(
                    selectedCustomer.getContactPerson()
            );
        }
    }

    private void clearFields() {
        customerView.getNameField().clear();
        customerView.getAddressField().clear();
        customerView.getPhoneField().clear();
        customerView.getContactField().clear();
    }
}
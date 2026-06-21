package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.model.Customer;
import com.example.app_orderprocessing.view.CustomerView;

public class CustomerController {

    private CustomerDao customerDao;
    private CustomerView customerView;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        customerDao = new CustomerDao();

        loadCustomers();

        customerView.getAddButton().setOnAction(event -> addCustomer());
        customerView.getDeleteButton().setOnAction(event -> deleteCustomer());
        customerView.getEditButton().setOnAction(event -> updateCustomer());

        customerView.getCustomerTable().setOnMouseClicked(
                event -> fillFields()
        );
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

        if (name.isBlank()
                || address.isBlank()
                || phone.isBlank()
                || contact.isBlank()) {

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

        boolean added = customerDao.addCustomer(customer);

        if (added) {
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

    private void deleteCustomer() {
        Customer selectedCustomer =
                customerView.getCustomerTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedCustomer == null) {
            customerView.getMessageLabel().setText(
                    "Выберите заказчика"
            );

            return;
        }

        boolean deleted =
                customerDao.deleteCustomer(selectedCustomer.getId());

        if (deleted) {
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
        Customer selectedCustomer =
                customerView.getCustomerTable()
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

    private void updateCustomer() {
        Customer selectedCustomer =
                customerView.getCustomerTable()
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

        if (name.isBlank()
                || address.isBlank()
                || phone.isBlank()
                || contact.isBlank()) {

            customerView.getMessageLabel().setText(
                    "Заполните все поля"
            );

            return;
        }

        selectedCustomer.setCustomerName(name);
        selectedCustomer.setAddress(address);
        selectedCustomer.setPhoneNumber(phone);
        selectedCustomer.setContactPerson(contact);

        boolean updated =
                customerDao.updateCustomer(selectedCustomer);

        if (updated) {
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

    private void clearFields() {
        customerView.getNameField().clear();
        customerView.getAddressField().clear();
        customerView.getPhoneField().clear();
        customerView.getContactField().clear();
    }
}
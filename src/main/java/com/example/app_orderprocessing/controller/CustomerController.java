package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.view.CustomerView;

public class CustomerController {

    private CustomerDao customerDao;
    private CustomerView customerView;

    public CustomerController(CustomerView customerView) {
        this.customerView = customerView;
        customerDao = new CustomerDao();

        loadCustomers();
    }

    public void loadCustomers() {
        customerView.getCustomerTable().getItems().setAll(
                customerDao.getAllCustomers()
        );
    }
}
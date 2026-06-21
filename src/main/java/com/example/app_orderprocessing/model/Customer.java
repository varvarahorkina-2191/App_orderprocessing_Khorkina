package com.example.app_orderprocessing.model;

public class Customer {

    private int id;
    private String customerName;
    private String address;
    private String phoneNumber;
    private String contactPerson;

    public Customer() {
    }

    public Customer(
            int id,
            String customerName,
            String address,
            String phoneNumber,
            String contactPerson
    ) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactPerson = contactPerson;
    }

    public Customer(
            String customerName,
            String address,
            String phoneNumber,
            String contactPerson
    ) {
        this.customerName = customerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.contactPerson = contactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
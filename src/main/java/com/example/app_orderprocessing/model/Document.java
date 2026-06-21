package com.example.app_orderprocessing.model;

import java.time.LocalDate;

public class Document {

    private int id;
    private int customerId;
    private String documentNumber;
    private LocalDate purchaseDate;

    public Document() {
    }

    public Document(
            int id,
            int customerId,
            String documentNumber,
            LocalDate purchaseDate
    ) {
        this.id = id;
        this.customerId = customerId;
        this.documentNumber = documentNumber;
        this.purchaseDate = purchaseDate;
    }

    public Document(
            int customerId,
            String documentNumber,
            LocalDate purchaseDate
    ) {
        this.customerId = customerId;
        this.documentNumber = documentNumber;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
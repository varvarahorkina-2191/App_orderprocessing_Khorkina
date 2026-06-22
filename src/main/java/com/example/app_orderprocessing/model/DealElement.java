package com.example.app_orderprocessing.model;

import java.math.BigDecimal;

public class DealElement {

    private int id;
    private int documentId;
    private int itemId;
    private int deliveryId;
    private int amount;
    private BigDecimal deliveryPrice;

    private String documentNumber;
    private String itemName;
    private String deliveryName;

    public DealElement() {
    }

    public DealElement(
            int documentId,
            int itemId,
            int deliveryId,
            int amount,
            BigDecimal deliveryPrice
    ) {
        this.documentId = documentId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.amount = amount;
        this.deliveryPrice = deliveryPrice;
    }

    public DealElement(
            int id,
            int documentId,
            int itemId,
            int deliveryId,
            int amount,
            BigDecimal deliveryPrice,
            String documentNumber,
            String itemName,
            String deliveryName
    ) {
        this.id = id;
        this.documentId = documentId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.amount = amount;
        this.deliveryPrice = deliveryPrice;
        this.documentNumber = documentNumber;
        this.itemName = itemName;
        this.deliveryName = deliveryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }
}
package com.example.app_orderprocessing.model;

public class DealElement {

    private int id;
    private int documentId;
    private int itemId;
    private int deliveryId;
    private int amount;
    private double deliveryPrice;

    public DealElement() {
    }

    public DealElement(
            int id,
            int documentId,
            int itemId,
            int deliveryId,
            int amount,
            double deliveryPrice
    ) {
        this.id = id;
        this.documentId = documentId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.amount = amount;
        this.deliveryPrice = deliveryPrice;
    }

    public DealElement(
            int documentId,
            int itemId,
            int deliveryId,
            int amount,
            double deliveryPrice
    ) {
        this.documentId = documentId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.amount = amount;
        this.deliveryPrice = deliveryPrice;
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

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}

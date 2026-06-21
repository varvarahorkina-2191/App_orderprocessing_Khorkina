package com.example.app_orderprocessing.model;

public class Item {

    private int id;
    private String itemName;
    private double price;
    private String itemInformation;
    private boolean hasDelivery;

    public Item() {
    }

    public Item(
            int id,
            String itemName,
            double price,
            String itemInformation,
            boolean hasDelivery
    ) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.itemInformation = itemInformation;
        this.hasDelivery = hasDelivery;
    }

    public Item(
            String itemName,
            double price,
            String itemInformation,
            boolean hasDelivery
    ) {
        this.itemName = itemName;
        this.price = price;
        this.itemInformation = itemInformation;
        this.hasDelivery = hasDelivery;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItemInformation() {
        return itemInformation;
    }

    public void setItemInformation(String itemInformation) {
        this.itemInformation = itemInformation;
    }

    public boolean isHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }
}
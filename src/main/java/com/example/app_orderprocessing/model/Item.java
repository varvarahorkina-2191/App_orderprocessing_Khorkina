package com.example.app_orderprocessing.model;

import java.math.BigDecimal;

public class Item {

    private int id;
    private String itemName;
    private BigDecimal price;
    private String itemInformation;
    private boolean hasDelivery;

    public Item() {
    }

    public Item(
            String itemName,
            BigDecimal price,
            String itemInformation,
            boolean hasDelivery
    ) {
        this.itemName = itemName;
        this.price = price;
        this.itemInformation = itemInformation;
        this.hasDelivery = hasDelivery;
    }

    public Item(
            int id,
            String itemName,
            BigDecimal price,
            String itemInformation,
            boolean hasDelivery
    ) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public boolean getHasDelivery() {
        return hasDelivery;
    }

    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }
}
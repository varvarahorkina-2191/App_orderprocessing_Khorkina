package com.example.app_orderprocessing.model;

public class ItemDelivery {

    private int itemId;
    private int deliveryId;

    private String itemName;
    private String deliveryName;

    public ItemDelivery() {
    }

    public ItemDelivery(
            int itemId,
            int deliveryId
    ) {
        this.itemId = itemId;
        this.deliveryId = deliveryId;
    }

    public ItemDelivery(
            int itemId,
            int deliveryId,
            String itemName,
            String deliveryName
    ) {
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.itemName = itemName;
        this.deliveryName = deliveryName;
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
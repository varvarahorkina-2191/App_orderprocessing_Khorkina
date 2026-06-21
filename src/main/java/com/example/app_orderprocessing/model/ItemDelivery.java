package com.example.app_orderprocessing.model;

public class ItemDelivery {

    private int itemId;
    private int deliveryId;

    public ItemDelivery() {
    }

    public ItemDelivery(int itemId, int deliveryId) {
        this.itemId = itemId;
        this.deliveryId = deliveryId;
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
}
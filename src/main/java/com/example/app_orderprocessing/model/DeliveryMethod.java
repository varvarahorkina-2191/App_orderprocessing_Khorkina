package com.example.app_orderprocessing.model;

import java.math.BigDecimal;

public class DeliveryMethod {

    private int id;
    private String name;
    private BigDecimal basicPrice;
    private String deliverySpeed;

    public DeliveryMethod() {
    }

    public DeliveryMethod(
            String name,
            BigDecimal basicPrice,
            String deliverySpeed
    ) {
        this.name = name;
        this.basicPrice = basicPrice;
        this.deliverySpeed = deliverySpeed;
    }

    public DeliveryMethod(
            int id,
            String name,
            BigDecimal basicPrice,
            String deliverySpeed
    ) {
        this.id = id;
        this.name = name;
        this.basicPrice = basicPrice;
        this.deliverySpeed = deliverySpeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public String getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(String deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }
}
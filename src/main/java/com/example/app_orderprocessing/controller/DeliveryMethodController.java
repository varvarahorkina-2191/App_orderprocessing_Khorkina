package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DeliveryMethodDao;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.DeliveryMethodView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;

public class DeliveryMethodController {

    private DeliveryMethodDao deliveryMethodDao;
    private DeliveryMethodView deliveryMethodView;
    private User user;

    public DeliveryMethodController(
            DeliveryMethodView deliveryMethodView,
            User user
    ) {
        this.deliveryMethodView = deliveryMethodView;
        this.user = user;

        deliveryMethodDao = new DeliveryMethodDao();

        loadDeliveryMethods();
        checkRole();

        deliveryMethodView
                .getAddButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                addDeliveryMethod();
                            }
                        }
                );

        deliveryMethodView
                .getEditButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                updateDeliveryMethod();
                            }
                        }
                );

        deliveryMethodView
                .getDeleteButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteDeliveryMethod();
                            }
                        }
                );

        deliveryMethodView
                .getDeliveryMethodTable()
                .setOnMouseClicked(
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                fillFields();
                            }
                        }
                );
    }

    private void checkRole() {
        if (user.getActiveRoleId() == 2) {
            deliveryMethodView
                    .getDeleteButton()
                    .setDisable(true);
        }
    }

    public void loadDeliveryMethods() {
        deliveryMethodView
                .getDeliveryMethodTable()
                .getItems()
                .setAll(
                        deliveryMethodDao
                                .getAllDeliveryMethods()
                );
    }

    private void addDeliveryMethod() {
        String name =
                deliveryMethodView
                        .getNameField()
                        .getText();

        String priceText =
                deliveryMethodView
                        .getBasicPriceField()
                        .getText();

        String deliverySpeed =
                deliveryMethodView
                        .getDeliverySpeedField()
                        .getText();

        if (name.isEmpty()
                || priceText.isEmpty()
                || deliverySpeed.isEmpty()) {

            deliveryMethodView
                    .getMessageLabel()
                    .setText("Заполните все поля");

            return;
        }

        BigDecimal basicPrice;

        try {
            String correctedPrice =
                    priceText.replace(",", ".");

            basicPrice =
                    new BigDecimal(correctedPrice);

        } catch (NumberFormatException e) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Стоимость должна быть числом"
                    );

            return;
        }

        if (basicPrice.compareTo(BigDecimal.ZERO) < 0) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Стоимость не может быть отрицательной"
                    );

            return;
        }

        DeliveryMethod deliveryMethod =
                new DeliveryMethod(
                        name,
                        basicPrice,
                        deliverySpeed
                );

        boolean added;

        added = deliveryMethodDao
                .addDeliveryMethod(deliveryMethod);

        if (added == true) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки добавлен"
                    );

            clearFields();
            loadDeliveryMethods();

        } else {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Не удалось добавить способ доставки. " +
                                    "Возможно, такое название уже существует"
                    );
        }
    }

    private void updateDeliveryMethod() {
        DeliveryMethod selectedDeliveryMethod =
                deliveryMethodView
                        .getDeliveryMethodTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDeliveryMethod == null) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Выберите способ доставки"
                    );

            return;
        }

        String name =
                deliveryMethodView
                        .getNameField()
                        .getText();

        String priceText =
                deliveryMethodView
                        .getBasicPriceField()
                        .getText();

        String deliverySpeed =
                deliveryMethodView
                        .getDeliverySpeedField()
                        .getText();

        if (name.isEmpty()
                || priceText.isEmpty()
                || deliverySpeed.isEmpty()) {

            deliveryMethodView
                    .getMessageLabel()
                    .setText("Заполните все поля");

            return;
        }

        BigDecimal basicPrice;

        try {
            String correctedPrice =
                    priceText.replace(",", ".");

            basicPrice =
                    new BigDecimal(correctedPrice);

        } catch (NumberFormatException e) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Стоимость должна быть числом"
                    );

            return;
        }

        if (basicPrice.compareTo(BigDecimal.ZERO) < 0) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Стоимость не может быть отрицательной"
                    );

            return;
        }

        selectedDeliveryMethod.setName(name);

        selectedDeliveryMethod.setBasicPrice(
                basicPrice
        );

        selectedDeliveryMethod.setDeliverySpeed(
                deliverySpeed
        );

        boolean updated;

        updated = deliveryMethodDao
                .updateDeliveryMethod(
                        selectedDeliveryMethod
                );

        if (updated == true) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки изменён"
                    );

            clearFields();
            loadDeliveryMethods();

        } else {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Не удалось изменить способ доставки"
                    );
        }
    }

    private void deleteDeliveryMethod() {
        if (user.getActiveRoleId() == 2) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "У вас нет права на удаление"
                    );

            return;
        }

        DeliveryMethod selectedDeliveryMethod =
                deliveryMethodView
                        .getDeliveryMethodTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDeliveryMethod == null) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Выберите способ доставки"
                    );

            return;
        }

        boolean deleted;

        deleted = deliveryMethodDao
                .deleteDeliveryMethod(
                        selectedDeliveryMethod.getId()
                );

        if (deleted == true) {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки удалён"
                    );

            clearFields();
            loadDeliveryMethods();

        } else {
            deliveryMethodView
                    .getMessageLabel()
                    .setText(
                            "Не удалось удалить способ доставки. " +
                                    "Возможно, он используется для товара или сделки"
                    );
        }
    }

    private void fillFields() {
        DeliveryMethod selectedDeliveryMethod =
                deliveryMethodView
                        .getDeliveryMethodTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDeliveryMethod != null) {
            deliveryMethodView
                    .getNameField()
                    .setText(
                            selectedDeliveryMethod.getName()
                    );

            deliveryMethodView
                    .getBasicPriceField()
                    .setText(
                            selectedDeliveryMethod
                                    .getBasicPrice()
                                    .toString()
                    );

            deliveryMethodView
                    .getDeliverySpeedField()
                    .setText(
                            selectedDeliveryMethod
                                    .getDeliverySpeed()
                    );
        }
    }

    private void clearFields() {
        deliveryMethodView
                .getNameField()
                .clear();

        deliveryMethodView
                .getBasicPriceField()
                .clear();

        deliveryMethodView
                .getDeliverySpeedField()
                .clear();

        deliveryMethodView
                .getDeliveryMethodTable()
                .getSelectionModel()
                .clearSelection();
    }
}
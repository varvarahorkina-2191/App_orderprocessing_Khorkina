package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DeliveryMethodDao;
import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.dao.ItemDeliveryDao;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.ItemDelivery;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.ItemDeliveryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ItemDeliveryController {

    private ItemDeliveryDao itemDeliveryDao;
    private ItemDao itemDao;
    private DeliveryMethodDao deliveryMethodDao;

    private ItemDeliveryView itemDeliveryView;
    private User user;

    private ArrayList<Item> items;
    private ArrayList<DeliveryMethod> deliveryMethods;

    public ItemDeliveryController(
            ItemDeliveryView itemDeliveryView,
            User user
    ) {
        this.itemDeliveryView = itemDeliveryView;
        this.user = user;

        itemDeliveryDao = new ItemDeliveryDao();
        itemDao = new ItemDao();
        deliveryMethodDao = new DeliveryMethodDao();

        loadItems();
        loadDeliveryMethods();
        loadItemDeliveries();
        checkRole();

        itemDeliveryView
                .getAddButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                addItemDelivery();
                            }
                        }
                );

        itemDeliveryView
                .getEditButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                updateItemDelivery();
                            }
                        }
                );

        itemDeliveryView
                .getDeleteButton()
                .setOnAction(
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                deleteItemDelivery();
                            }
                        }
                );

        itemDeliveryView
                .getItemDeliveryTable()
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
            itemDeliveryView
                    .getDeleteButton()
                    .setDisable(true);
        }
    }

    private void loadItems() {
        items = itemDao.getAllItems();

        itemDeliveryView
                .getItemComboBox()
                .getItems()
                .clear();

        int i = 0;

        while (i < items.size()) {
            Item item = items.get(i);

            itemDeliveryView
                    .getItemComboBox()
                    .getItems()
                    .add(item.getItemName());

            i++;
        }
    }

    private void loadDeliveryMethods() {
        deliveryMethods =
                deliveryMethodDao.getAllDeliveryMethods();

        itemDeliveryView
                .getDeliveryComboBox()
                .getItems()
                .clear();

        int i = 0;

        while (i < deliveryMethods.size()) {
            DeliveryMethod deliveryMethod =
                    deliveryMethods.get(i);

            itemDeliveryView
                    .getDeliveryComboBox()
                    .getItems()
                    .add(deliveryMethod.getName());

            i++;
        }
    }

    public void loadItemDeliveries() {
        itemDeliveryView
                .getItemDeliveryTable()
                .getItems()
                .setAll(
                        itemDeliveryDao.getAllItemDeliveries()
                );
    }

    private void addItemDelivery() {
        Item selectedItem = findSelectedItem();

        DeliveryMethod selectedDeliveryMethod =
                findSelectedDeliveryMethod();

        if (selectedItem == null
                || selectedDeliveryMethod == null) {

            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Выберите товар и способ доставки"
                    );

            return;
        }

        ItemDelivery itemDelivery =
                new ItemDelivery(
                        selectedItem.getId(),
                        selectedDeliveryMethod.getId()
                );

        boolean added;

        added = itemDeliveryDao.addItemDelivery(
                itemDelivery
        );

        if (added == true) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки назначен товару"
                    );

            clearFields();
            loadItemDeliveries();

        } else {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Не удалось добавить запись. " +
                                    "Возможно, этот способ доставки уже назначен товару"
                    );
        }
    }

    private void updateItemDelivery() {
        ItemDelivery selectedItemDelivery =
                itemDeliveryView
                        .getItemDeliveryTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItemDelivery == null) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Выберите запись в таблице"
                    );

            return;
        }

        Item selectedItem = findSelectedItem();

        DeliveryMethod selectedDeliveryMethod =
                findSelectedDeliveryMethod();

        if (selectedItem == null
                || selectedDeliveryMethod == null) {

            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Выберите товар и способ доставки"
                    );

            return;
        }

        int oldItemId =
                selectedItemDelivery.getItemId();

        int oldDeliveryId =
                selectedItemDelivery.getDeliveryId();

        ItemDelivery newItemDelivery =
                new ItemDelivery(
                        selectedItem.getId(),
                        selectedDeliveryMethod.getId()
                );

        boolean updated;

        updated = itemDeliveryDao.updateItemDelivery(
                newItemDelivery,
                oldItemId,
                oldDeliveryId
        );

        if (updated == true) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки товара изменён"
                    );

            clearFields();
            loadItemDeliveries();

        } else {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Не удалось изменить запись. " +
                                    "Возможно, такая связь уже существует"
                    );
        }
    }

    private void deleteItemDelivery() {
        if (user.getActiveRoleId() == 2) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "У вас нет права на удаление"
                    );

            return;
        }

        ItemDelivery selectedItemDelivery =
                itemDeliveryView
                        .getItemDeliveryTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItemDelivery == null) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Выберите запись в таблице"
                    );

            return;
        }

        boolean deleted;

        deleted = itemDeliveryDao.deleteItemDelivery(
                selectedItemDelivery.getItemId(),
                selectedItemDelivery.getDeliveryId()
        );

        if (deleted == true) {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Способ доставки удалён у товара"
                    );

            clearFields();
            loadItemDeliveries();

        } else {
            itemDeliveryView
                    .getMessageLabel()
                    .setText(
                            "Не удалось удалить запись"
                    );
        }
    }

    private Item findSelectedItem() {
        String selectedItemName =
                itemDeliveryView
                        .getItemComboBox()
                        .getValue();

        if (selectedItemName == null) {
            return null;
        }

        int i = 0;

        while (i < items.size()) {
            Item item = items.get(i);

            if (item.getItemName().equals(
                    selectedItemName
            )) {
                return item;
            }

            i++;
        }

        return null;
    }

    private DeliveryMethod findSelectedDeliveryMethod() {
        String selectedDeliveryName =
                itemDeliveryView
                        .getDeliveryComboBox()
                        .getValue();

        if (selectedDeliveryName == null) {
            return null;
        }

        int i = 0;

        while (i < deliveryMethods.size()) {
            DeliveryMethod deliveryMethod =
                    deliveryMethods.get(i);

            if (deliveryMethod.getName().equals(
                    selectedDeliveryName
            )) {
                return deliveryMethod;
            }

            i++;
        }

        return null;
    }

    private void fillFields() {
        ItemDelivery selectedItemDelivery =
                itemDeliveryView
                        .getItemDeliveryTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItemDelivery != null) {
            itemDeliveryView
                    .getItemComboBox()
                    .setValue(
                            selectedItemDelivery.getItemName()
                    );

            itemDeliveryView
                    .getDeliveryComboBox()
                    .setValue(
                            selectedItemDelivery.getDeliveryName()
                    );
        }
    }

    private void clearFields() {
        itemDeliveryView
                .getItemComboBox()
                .getSelectionModel()
                .clearSelection();

        itemDeliveryView
                .getDeliveryComboBox()
                .getSelectionModel()
                .clearSelection();

        itemDeliveryView
                .getItemDeliveryTable()
                .getSelectionModel()
                .clearSelection();
    }
}
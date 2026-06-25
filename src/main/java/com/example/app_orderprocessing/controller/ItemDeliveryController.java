package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DeliveryMethodDao;
import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.dao.ItemDeliveryDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.ItemDelivery;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.utilities.ConfirmationDialog;
import com.example.app_orderprocessing.view.ItemDeliveryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ItemDeliveryController {

    private ItemDeliveryDao itemDeliveryDao;
    private ItemDao itemDao;
    private DeliveryMethodDao deliveryMethodDao;
    private RoleDao roleDao;

    private ItemDeliveryView view;
    private User user;

    private ArrayList<Item> items;
    private ArrayList<DeliveryMethod> deliveryMethods;

    public ItemDeliveryController(ItemDeliveryView view, User user) {
        this.view = view;
        this.user = user;

        itemDeliveryDao = new ItemDeliveryDao();
        itemDao = new ItemDao();
        deliveryMethodDao = new DeliveryMethodDao();
        roleDao = new RoleDao();

        loadItems();
        loadDeliveryMethods();
        loadItemDeliveries();
        configureAccess();
        connectEvents();
    }

    private void connectEvents() {
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveItemDelivery(false);
            }
        });

        view.getEditButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveItemDelivery(true);
            }
        });

        view.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteItemDelivery();
            }
        });

        view.getItemDeliveryTable().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fillFields();
            }
        });
    }

    private void configureAccess() {
        if (isManager()) {
            view.getDeleteButton().setDisable(true);
        }

        if (isCustomer()) {
            view.enableCustomerMode();
        }
    }

    private boolean isManager() {
        return hasRole("MANAGER");
    }

    private boolean isCustomer() {
        return hasRole("CUSTOMER");
    }

    private boolean hasRole(String name) {
        Role role = roleDao.findByName(name);
        return role != null && user.getActiveRoleId() == role.getId();
    }

    private void loadItems() {
        items = itemDao.getAllItems();
        view.getItemComboBox().getItems().clear();

        for (Item item : items) {
            view.getItemComboBox().getItems().add(item.getItemName());
        }
    }

    private void loadDeliveryMethods() {
        deliveryMethods = deliveryMethodDao.getAllDeliveryMethods();
        view.getDeliveryComboBox().getItems().clear();

        for (DeliveryMethod delivery : deliveryMethods) {
            view.getDeliveryComboBox().getItems().add(delivery.getName());
        }
    }

    public void loadItemDeliveries() {
        view.getItemDeliveryTable().getItems().setAll(itemDeliveryDao.getAllItemDeliveries());
    }

    private void saveItemDelivery(boolean edit) {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение");
            return;
        }

        ItemDelivery selected = getSelectedItemDelivery();

        if (edit && selected == null) {
            showMessage("Выберите запись в таблице");
            return;
        }

        Item item = findSelectedItem();
        DeliveryMethod delivery = findSelectedDeliveryMethod();

        if (item == null || delivery == null) {
            showMessage("Выберите товар и способ доставки");
            return;
        }

        if (item.isHasDelivery() == false) {
            showMessage("Для выбранного товара доставка недоступна");
            return;
        }

        ItemDelivery itemDelivery = new ItemDelivery(item.getId(), delivery.getId());
        boolean result;

        if (edit) {
            result = itemDeliveryDao.updateItemDelivery(
                    itemDelivery,
                    selected.getItemId(),
                    selected.getDeliveryId()
            );
        }
        else {
            result = itemDeliveryDao.addItemDelivery(itemDelivery);
        }

        if (result) {
            if (edit) {
                showMessage("Способ доставки товара изменён");
            }
            else {
                showMessage("Способ доставки назначен товару");
            }

            clearFields();
            loadItemDeliveries();
        }
        else {
            showMessage("Не удалось сохранить запись. Возможно, такая связь уже существует");
        }
    }

    private void deleteItemDelivery() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        ItemDelivery itemDelivery = getSelectedItemDelivery();

        if (itemDelivery == null) {
            showMessage("Выберите запись в таблице");
            return;
        }

        String text = "Удалить способ доставки «" + itemDelivery.getDeliveryName()
                + "» у товара «" + itemDelivery.getItemName() + "»?";

        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = itemDeliveryDao.deleteItemDelivery(
                itemDelivery.getItemId(),
                itemDelivery.getDeliveryId()
        );

        if (deleted) {
            showMessage("Способ доставки удалён у товара");
            clearFields();
            loadItemDeliveries();
        }
        else {
            showMessage("Не удалось удалить запись. Возможно, она используется в сделке");
        }
    }

    private Item findSelectedItem() {
        String name = view.getItemComboBox().getValue();

        if (name != null) {
            for (Item item : items) {
                if (item.getItemName().equals(name)) {
                    return item;
                }
            }
        }

        return null;
    }

    private DeliveryMethod findSelectedDeliveryMethod() {
        String name = view.getDeliveryComboBox().getValue();

        if (name != null) {
            for (DeliveryMethod delivery : deliveryMethods) {
                if (delivery.getName().equals(name)) {
                    return delivery;
                }
            }
        }

        return null;
    }

    private ItemDelivery getSelectedItemDelivery() {
        return view.getItemDeliveryTable().getSelectionModel().getSelectedItem();
    }

    private void fillFields() {
        if (isCustomer()) {
            return;
        }

        ItemDelivery itemDelivery = getSelectedItemDelivery();

        if (itemDelivery == null) {
            return;
        }

        view.getItemComboBox().setValue(itemDelivery.getItemName());
        view.getDeliveryComboBox().setValue(itemDelivery.getDeliveryName());
    }

    private void clearFields() {
        view.getItemComboBox().getSelectionModel().clearSelection();
        view.getDeliveryComboBox().getSelectionModel().clearSelection();
        view.getItemDeliveryTable().getSelectionModel().clearSelection();
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
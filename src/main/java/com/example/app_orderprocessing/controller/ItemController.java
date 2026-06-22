package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.ItemView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;

public class ItemController {

    private ItemDao itemDao;
    private ItemView itemView;
    private User user;

    public ItemController(
            ItemView itemView,
            User user
    ) {
        this.itemView = itemView;
        this.user = user;

        itemDao = new ItemDao();

        loadItems();
        checkRole();

        itemView.getAddButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addItem();
                    }
                }
        );

        itemView.getEditButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateItem();
                    }
                }
        );

        itemView.getDeleteButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteItem();
                    }
                }
        );

        itemView.getItemTable().setOnMouseClicked(
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
            itemView
                    .getDeleteButton()
                    .setDisable(true);
        }
    }

    public void loadItems() {
        itemView
                .getItemTable()
                .getItems()
                .setAll(itemDao.getAllItems());
    }

    private void addItem() {
        String name =
                itemView.getNameField().getText();

        String priceText =
                itemView.getPriceField().getText();

        String information =
                itemView.getInformationArea().getText();

        boolean hasDelivery =
                itemView
                        .getDeliveryCheckBox()
                        .isSelected();

        if (name.isEmpty() || priceText.isEmpty()) {
            itemView.getMessageLabel().setText(
                    "Введите название и цену товара"
            );

            return;
        }

        BigDecimal price;

        try {
            String correctedPrice =
                    priceText.replace(",", ".");

            price = new BigDecimal(correctedPrice);

        } catch (NumberFormatException e) {
            itemView.getMessageLabel().setText(
                    "Цена должна быть числом"
            );

            return;
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            itemView.getMessageLabel().setText(
                    "Цена должна быть больше нуля"
            );

            return;
        }

        Item item = new Item(
                name,
                price,
                information,
                hasDelivery
        );

        boolean added;
        added = itemDao.addItem(item);

        if (added == true) {
            itemView.getMessageLabel().setText(
                    "Товар добавлен"
            );

            clearFields();
            loadItems();

        } else {
            itemView.getMessageLabel().setText(
                    "Не удалось добавить товар. " +
                            "Проверьте, не существует ли товар с таким названием"
            );
        }
    }

    private void updateItem() {
        Item selectedItem =
                itemView
                        .getItemTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItem == null) {
            itemView.getMessageLabel().setText(
                    "Выберите товар"
            );

            return;
        }

        String name =
                itemView.getNameField().getText();

        String priceText =
                itemView.getPriceField().getText();

        String information =
                itemView.getInformationArea().getText();

        boolean hasDelivery =
                itemView
                        .getDeliveryCheckBox()
                        .isSelected();

        if (name.isEmpty() || priceText.isEmpty()) {
            itemView.getMessageLabel().setText(
                    "Введите название и цену товара"
            );

            return;
        }

        BigDecimal price;

        try {
            String correctedPrice =
                    priceText.replace(",", ".");

            price = new BigDecimal(correctedPrice);

        } catch (NumberFormatException e) {
            itemView.getMessageLabel().setText(
                    "Цена должна быть числом"
            );

            return;
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            itemView.getMessageLabel().setText(
                    "Цена должна быть больше нуля"
            );

            return;
        }

        selectedItem.setItemName(name);
        selectedItem.setPrice(price);
        selectedItem.setItemInformation(information);
        selectedItem.setHasDelivery(hasDelivery);

        boolean updated;
        updated = itemDao.updateItem(selectedItem);

        if (updated == true) {
            itemView.getMessageLabel().setText(
                    "Данные товара изменены"
            );

            clearFields();
            loadItems();

        } else {
            itemView.getMessageLabel().setText(
                    "Не удалось изменить товар"
            );
        }
    }

    private void deleteItem() {
        if (user.getActiveRoleId() == 2) {
            itemView.getMessageLabel().setText(
                    "У вас нет права на удаление"
            );

            return;
        }

        Item selectedItem =
                itemView
                        .getItemTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItem == null) {
            itemView.getMessageLabel().setText(
                    "Выберите товар"
            );

            return;
        }

        boolean deleted;

        deleted = itemDao.deleteItem(
                selectedItem.getId()
        );

        if (deleted == true) {
            itemView.getMessageLabel().setText(
                    "Товар удалён"
            );

            clearFields();
            loadItems();

        } else {
            itemView.getMessageLabel().setText(
                    "Не удалось удалить товар. " +
                            "Возможно, товар используется в сделке"
            );
        }
    }

    private void fillFields() {
        Item selectedItem =
                itemView
                        .getItemTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedItem != null) {
            itemView.getNameField().setText(
                    selectedItem.getItemName()
            );

            itemView.getPriceField().setText(
                    selectedItem.getPrice().toString()
            );

            itemView.getInformationArea().setText(
                    selectedItem.getItemInformation()
            );

            itemView.getDeliveryCheckBox().setSelected(
                    selectedItem.getHasDelivery()
            );
        }
    }

    private void clearFields() {
        itemView.getNameField().clear();
        itemView.getPriceField().clear();
        itemView.getInformationArea().clear();

        itemView
                .getDeliveryCheckBox()
                .setSelected(true);

        itemView
                .getItemTable()
                .getSelectionModel()
                .clearSelection();
    }
}
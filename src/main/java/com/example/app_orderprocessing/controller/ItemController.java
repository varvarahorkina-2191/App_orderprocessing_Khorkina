package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.ConfirmationDialog;
import com.example.app_orderprocessing.view.ItemView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;

public class ItemController implements EventHandler<ActionEvent> {

    private ItemView view;
    private ItemDao itemDao;
    private RoleDao roleDao;
    private User user;

    private Item selectedItem;

    public ItemController(ItemView view, User user) {
        this.view = view;
        this.user = user;

        itemDao = new ItemDao();
        roleDao = new RoleDao();

        loadItems();
        configureAccess();
        connectEvents();
    }

    private void connectEvents() {
        view.getAddButton().setOnAction(this);
        view.getEditButton().setOnAction(this);
        view.getDeleteButton().setOnAction(this);
        view.getSearchButton().setOnAction(this);
        view.getResetSearchButton().setOnAction(this);
        view.getSearchField().setOnAction(this);

        view.getItemTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Item>() {
                    @Override
                    public void changed(ObservableValue<? extends Item> value,
                                        Item oldItem,
                                        Item newItem) {
                        selectItem(newItem);
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddButton()) {
            saveItem(false);
        } else if (source == view.getEditButton()) {
            saveItem(true);
        } else if (source == view.getDeleteButton()) {
            deleteItem();
        } else if (source == view.getSearchButton() || source == view.getSearchField()) {
            searchItems();
        } else if (source == view.getResetSearchButton()) {
            resetSearch();
        }
    }

    private void configureAccess() {
        if (isManager()) {
            view.getDeleteButton().setDisable(true);
        }

        if (isCustomer()) {
            view.getAddButton().setDisable(true);
            view.getEditButton().setDisable(true);
            view.getDeleteButton().setDisable(true);
            view.getNameField().setDisable(true);
            view.getPriceField().setDisable(true);
            view.getInformationArea().setDisable(true);
            view.getDeliveryCheckBox().setDisable(true);

            showMessage("Доступен только просмотр товаров");
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
        view.getItemTable().getItems().setAll(itemDao.getAllItems());
    }

    private void searchItems() {
        String text = view.getSearchField().getText().trim();

        clearFields();

        if (text.isEmpty()) {
            loadItems();
            showMessage("Показаны все товары");
            return;
        }

        view.getItemTable().getItems().setAll(itemDao.searchItems(text));

        int count = view.getItemTable().getItems().size();

        if (count == 0) {
            showMessage("Товары не найдены");
        } else {
            showMessage("Найдено товаров: " + count);
        }
    }

    private void resetSearch() {
        view.getSearchField().clear();
        clearFields();
        loadItems();
        showMessage("Поиск сброшен");
    }

    private void selectItem(Item item) {
        selectedItem = item;

        if (item == null || isCustomer()) {
            return;
        }

        view.getNameField().setText(item.getItemName());
        view.getPriceField().setText(item.getPrice().toString());
        view.getInformationArea().setText(item.getItemInformation());
        view.getDeliveryCheckBox().setSelected(item.isHasDelivery());
    }

    private void saveItem(boolean edit) {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение товаров");
            return;
        }

        if (edit && selectedItem == null) {
            showMessage("Выберите товар");
            return;
        }

        Item item = readItem();

        if (item == null) {
            return;
        }

        boolean result;

        if (edit) {
            selectedItem.setItemName(item.getItemName());
            selectedItem.setPrice(item.getPrice());
            selectedItem.setItemInformation(item.getItemInformation());
            selectedItem.setHasDelivery(item.isHasDelivery());

            result = itemDao.updateItem(selectedItem);
        } else {
            result = itemDao.addItem(item);
        }

        if (result) {
            if (edit) {
                showMessage("Товар изменён");
            } else {
                showMessage("Товар добавлен");
            }

            clearFields();
            view.getSearchField().clear();
            loadItems();
        } else {
            showMessage("Не удалось сохранить товар");
        }
    }

    private void deleteItem() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        if (selectedItem == null) {
            showMessage("Выберите товар");
            return;
        }

        String text = "Удалить товар «" + selectedItem.getItemName() + "»?";
        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = itemDao.deleteItem(selectedItem.getId());

        if (deleted) {
            showMessage("Товар удалён");
            clearFields();
            loadItems();
        } else {
            showMessage("Не удалось удалить товар. Возможно, он используется в сделке");
        }
    }

    private Item readItem() {
        String name = view.getNameField().getText().trim();
        String priceText = view.getPriceField().getText().trim();
        String information = view.getInformationArea().getText().trim();
        boolean hasDelivery = view.getDeliveryCheckBox().isSelected();

        if (name.isEmpty() || priceText.isEmpty()) {
            showMessage("Заполните название и цену");
            return null;
        }

        BigDecimal price;

        try {
            price = new BigDecimal(priceText.replace(",", "."));
        } catch (NumberFormatException e) {
            showMessage("Цена указана неверно");
            return null;
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            showMessage("Цена должна быть больше нуля");
            return null;
        }

        return new Item(name, price, information, hasDelivery);
    }

    private void clearFields() {
        selectedItem = null;
        view.getItemTable().getSelectionModel().clearSelection();
        view.getNameField().clear();
        view.getPriceField().clear();
        view.getInformationArea().clear();
        view.getDeliveryCheckBox().setSelected(false);
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
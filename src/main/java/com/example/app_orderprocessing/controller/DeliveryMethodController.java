package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DeliveryMethodDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.util.ConfirmationDialog;
import com.example.app_orderprocessing.view.DeliveryMethodView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;

public class DeliveryMethodController implements EventHandler<ActionEvent> {

    private DeliveryMethodView view;
    private DeliveryMethodDao deliveryMethodDao;
    private RoleDao roleDao;
    private User user;

    private DeliveryMethod selectedDeliveryMethod;

    public DeliveryMethodController(DeliveryMethodView view, User user) {
        this.view = view;
        this.user = user;

        deliveryMethodDao = new DeliveryMethodDao();
        roleDao = new RoleDao();

        loadDeliveryMethods();
        configureAccess();
        connectEvents();
    }

    private void connectEvents() {
        view.getAddButton().setOnAction(this);
        view.getEditButton().setOnAction(this);
        view.getDeleteButton().setOnAction(this);

        view.getDeliveryMethodTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<DeliveryMethod>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends DeliveryMethod> value,
                            DeliveryMethod oldValue,
                            DeliveryMethod newValue
                    ) {
                        selectDeliveryMethod(newValue);
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddButton()) {
            saveDeliveryMethod(false);
        } else if (source == view.getEditButton()) {
            saveDeliveryMethod(true);
        } else if (source == view.getDeleteButton()) {
            deleteDeliveryMethod();
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
            view.getBasicPriceField().setDisable(true);
            view.getDeliverySpeedField().setDisable(true);

            showMessage("Доступен только просмотр способов доставки");
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

    private void loadDeliveryMethods() {
        view.getDeliveryMethodTable().getItems().setAll(
                deliveryMethodDao.getAllDeliveryMethods()
        );
    }

    private void selectDeliveryMethod(DeliveryMethod delivery) {
        selectedDeliveryMethod = delivery;

        if (delivery == null || isCustomer()) {
            return;
        }

        view.getNameField().setText(delivery.getName());
        view.getBasicPriceField().setText(delivery.getBasicPrice().toString());
        view.getDeliverySpeedField().setText(delivery.getDeliverySpeed());
    }

    private void saveDeliveryMethod(boolean edit) {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение");
            return;
        }

        if (edit && selectedDeliveryMethod == null) {
            showMessage("Выберите способ доставки");
            return;
        }

        DeliveryMethod delivery = readDeliveryMethod();

        if (delivery == null) {
            return;
        }

        boolean result;

        if (edit) {
            selectedDeliveryMethod.setName(delivery.getName());
            selectedDeliveryMethod.setBasicPrice(delivery.getBasicPrice());
            selectedDeliveryMethod.setDeliverySpeed(delivery.getDeliverySpeed());

            result = deliveryMethodDao.updateDeliveryMethod(selectedDeliveryMethod);
        } else {
            result = deliveryMethodDao.addDeliveryMethod(delivery);
        }

        if (result) {
            if (edit) {
                showMessage("Способ доставки изменён");
            } else {
                showMessage("Способ доставки добавлен");
            }

            clearFields();
            loadDeliveryMethods();
        } else {
            showMessage("Не удалось сохранить способ доставки");
        }
    }

    private void deleteDeliveryMethod() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        if (selectedDeliveryMethod == null) {
            showMessage("Выберите способ доставки");
            return;
        }

        String text = "Удалить способ доставки «"
                + selectedDeliveryMethod.getName() + "»?";

        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = deliveryMethodDao.deleteDeliveryMethod(
                selectedDeliveryMethod.getId()
        );

        if (deleted) {
            showMessage("Способ доставки удалён");
            clearFields();
            loadDeliveryMethods();
        } else {
            showMessage(
                    "Не удалось удалить способ доставки. "
                            + "Возможно, он используется в сделке"
            );
        }
    }

    private DeliveryMethod readDeliveryMethod() {
        String name = view.getNameField().getText().trim();
        String priceText = view.getBasicPriceField().getText().trim();
        String speed = view.getDeliverySpeedField().getText().trim();

        if (name.isEmpty() || priceText.isEmpty() || speed.isEmpty()) {
            showMessage("Заполните все поля");
            return null;
        }

        BigDecimal price;

        try {
            price = new BigDecimal(priceText.replace(",", "."));
        } catch (NumberFormatException e) {
            showMessage("Стоимость указана неверно");
            return null;
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            showMessage("Стоимость не может быть отрицательной");
            return null;
        }

        return new DeliveryMethod(name, price, speed);
    }

    private void clearFields() {
        selectedDeliveryMethod = null;
        view.getDeliveryMethodTable().getSelectionModel().clearSelection();
        view.getNameField().clear();
        view.getBasicPriceField().clear();
        view.getDeliverySpeedField().clear();
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
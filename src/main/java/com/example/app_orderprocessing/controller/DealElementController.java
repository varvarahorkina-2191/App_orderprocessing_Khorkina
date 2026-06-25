package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DealElementDao;
import com.example.app_orderprocessing.dao.DocumentDao;
import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.dao.ItemDeliveryDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.DealElement;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.Document;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.utilities.ConfirmationDialog;
import com.example.app_orderprocessing.view.DealElementView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;
import java.util.List;

public class DealElementController implements EventHandler<ActionEvent> {

    private DealElementDao dealElementDao;
    private DocumentDao documentDao;
    private ItemDao itemDao;
    private ItemDeliveryDao itemDeliveryDao;
    private RoleDao roleDao;

    private DealElementView view;
    private User user;

    private List<Document> documents;
    private List<Item> items;
    private List<DeliveryMethod> deliveryMethods;

    private DealElement selectedDealElement;

    public DealElementController(DealElementView view, User user) {
        this.view = view;
        this.user = user;

        dealElementDao = new DealElementDao();
        documentDao = new DocumentDao();
        itemDao = new ItemDao();
        itemDeliveryDao = new ItemDeliveryDao();
        roleDao = new RoleDao();

        loadDocuments();
        loadItems();
        loadDealElements();
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

        view.getDealElementTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<DealElement>() {
                    @Override
                    public void changed(ObservableValue<? extends DealElement> value,
                                        DealElement oldValue,
                                        DealElement newValue) {
                        selectDealElement(newValue);
                    }
                }
        );

        view.getItemComboBox().valueProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> value,
                                        String oldValue,
                                        String newValue) {
                        loadDeliveryMethods();
                    }
                }
        );

        view.getDeliveryComboBox().valueProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> value,
                                        String oldValue,
                                        String newValue) {
                        fillDeliveryPrice();
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddButton()) {
            saveDealElement(false);
        }
        else if (source == view.getEditButton()) {
            saveDealElement(true);
        }
        else if (source == view.getDeleteButton()) {
            deleteDealElement();
        }
        else if (source == view.getSearchButton() || source == view.getSearchField()) {
            searchDealElements();
        }
        else if (source == view.getResetSearchButton()) {
            resetSearch();
        }
    }

    private void configureAccess() {
        view.getDeliveryPriceField().setEditable(false);

        if (isManager()) {
            view.getDeleteButton().setDisable(true);
        }

        if (isCustomer()) {
            view.getAddButton().setDisable(true);
            view.getEditButton().setDisable(true);
            view.getDeleteButton().setDisable(true);
            view.getDocumentComboBox().setDisable(true);
            view.getItemComboBox().setDisable(true);
            view.getDeliveryComboBox().setDisable(true);
            view.getAmountField().setDisable(true);
            view.getDeliveryPriceField().setDisable(true);

            showMessage("У вас нет доступа к элементам сделок");
        }
    }

    private boolean hasRole(String name) {
        Role role = roleDao.findByName(name);

        return role != null && user.getActiveRoleId() == role.getId();
    }

    private boolean isManager() {
        return hasRole("MANAGER");
    }

    private boolean isCustomer() {
        return hasRole("CUSTOMER");
    }

    private void loadDocuments() {
        documents = documentDao.getAllDocuments();
        view.getDocumentComboBox().getItems().clear();

        for (Document document : documents) {
            view.getDocumentComboBox().getItems().add(document.getDocumentNumber());
        }
    }

    private void loadItems() {
        items = itemDao.getAllItems();
        view.getItemComboBox().getItems().clear();

        for (Item item : items) {
            view.getItemComboBox().getItems().add(item.getItemName());
        }
    }

    public void loadDealElements() {
        view.getDealElementTable().getItems().setAll(dealElementDao.getAllDealElements());
    }

    private void searchDealElements() {
        String text = view.getSearchField().getText().trim();

        clearFields();

        if (text.isEmpty()) {
            loadDealElements();
            showMessage("Показаны все элементы сделок");
            return;
        }

        view.getDealElementTable().getItems().setAll(dealElementDao.searchDealElements(text));

        int count = view.getDealElementTable().getItems().size();

        if (count == 0) {
            showMessage("Элементы сделок не найдены");
        } else {
            showMessage("Найдено элементов: " + count);
        }
    }

    private void resetSearch() {
        view.getSearchField().clear();
        clearFields();
        loadDealElements();
        showMessage("Поиск сброшен");
    }

    private void loadDeliveryMethods() {
        Item item = findItem();

        view.getDeliveryComboBox().getItems().clear();
        view.getDeliveryComboBox().setValue(null);
        view.getDeliveryPriceField().clear();
        deliveryMethods = null;

        if (item == null) {
            return;
        }

        if (item.isHasDelivery() == false) {
            showMessage("Для товара доставка недоступна");
            return;
        }

        deliveryMethods = itemDeliveryDao.getDeliveryMethodsByItemId(item.getId());

        for (DeliveryMethod delivery : deliveryMethods) {
            view.getDeliveryComboBox().getItems().add(delivery.getName());
        }

        if (deliveryMethods.isEmpty()) {
            showMessage("Для товара способы доставки не назначены");
        } else {
            showMessage("");
        }
    }

    private void fillDeliveryPrice() {
        DeliveryMethod delivery = findDelivery();

        if (delivery == null) {
            view.getDeliveryPriceField().clear();
        } else {
            view.getDeliveryPriceField().setText(delivery.getBasicPrice().toString());
        }
    }

    private void selectDealElement(DealElement element) {
        selectedDealElement = element;

        if (element == null || isCustomer()) {
            return;
        }

        view.getDocumentComboBox().setValue(element.getDocumentNumber());
        view.getItemComboBox().setValue(element.getItemName());
        loadDeliveryMethods();
        view.getDeliveryComboBox().setValue(element.getDeliveryName());
        view.getAmountField().setText(String.valueOf(element.getAmount()));
        view.getDeliveryPriceField().setText(element.getDeliveryPrice().toString());
    }

    private void saveDealElement(boolean edit) {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение элементов сделки");
            return;
        }

        if (edit && selectedDealElement == null) {
            showMessage("Выберите элемент сделки");
            return;
        }

        Document document = findDocument();
        Item item = findItem();
        DeliveryMethod delivery = findDelivery();

        if (checkData(document, item, delivery) == false) {
            return;
        }

        Integer amount = readAmount();

        if (amount == null) {
            return;
        }

        BigDecimal price = delivery.getBasicPrice();

        if (edit) {
            selectedDealElement.setDocumentId(document.getId());
            selectedDealElement.setItemId(item.getId());
            selectedDealElement.setDeliveryId(delivery.getId());
            selectedDealElement.setAmount(amount);
            selectedDealElement.setDeliveryPrice(price);

            boolean result = dealElementDao.updateDealElement(selectedDealElement);
            finishSaving(result, "Элемент сделки изменён");
        } else {
            DealElement element = new DealElement(
                    document.getId(),
                    item.getId(),
                    delivery.getId(),
                    amount,
                    price
            );

            boolean result = dealElementDao.addDealElement(element);
            finishSaving(result, "Элемент сделки добавлен");
        }
    }

    private boolean checkData(Document document, Item item, DeliveryMethod delivery) {
        if (document == null) {
            showMessage("Выберите документ");
            return false;
        }

        if (item == null) {
            showMessage("Выберите товар");
            return false;
        }

        if (item.isHasDelivery() == false) {
            showMessage("Для товара доставка недоступна");
            return false;
        }

        if (delivery == null) {
            showMessage("Выберите способ доставки");
            return false;
        }

        if (dealElementDao.isDeliveryAvailable(item.getId(), delivery.getId()) == false) {
            showMessage("Этот способ доставки недоступен");
            return false;
        }

        return true;
    }

    private void finishSaving(boolean result, String message) {
        if (result) {
            showMessage(message);
            clearFields();
            view.getSearchField().clear();
            loadDealElements();
        } else {
            showMessage("Не удалось сохранить элемент сделки");
        }
    }

    private void deleteDealElement() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        if (selectedDealElement == null) {
            showMessage("Выберите элемент сделки");
            return;
        }

        String text = "Удалить товар «" + selectedDealElement.getItemName() + "»?";
        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = dealElementDao.deleteDealElement(selectedDealElement.getId());

        if (deleted) {
            showMessage("Элемент сделки удалён");
            clearFields();
            loadDealElements();
        } else {
            showMessage("Не удалось удалить элемент сделки");
        }
    }

    private Integer readAmount() {
        String text = view.getAmountField().getText().trim();

        try {
            int amount = Integer.parseInt(text);

            if (amount > 0) {
                return amount;
            }
        } catch (NumberFormatException e) {
            showMessage("Количество должно быть целым числом");
            return null;
        }

        showMessage("Количество должно быть больше нуля");
        return null;
    }

    private Document findDocument() {
        String number = view.getDocumentComboBox().getValue();

        if (number != null) {
            for (Document document : documents) {
                if (document.getDocumentNumber().equals(number)) {
                    return document;
                }
            }
        }

        return null;
    }

    private Item findItem() {
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

    private DeliveryMethod findDelivery() {
        String name = view.getDeliveryComboBox().getValue();

        if (name != null && deliveryMethods != null) {
            for (DeliveryMethod delivery : deliveryMethods) {
                if (delivery.getName().equals(name)) {
                    return delivery;
                }
            }
        }

        return null;
    }

    private void clearFields() {
        selectedDealElement = null;
        view.getDealElementTable().getSelectionModel().clearSelection();
        view.getDocumentComboBox().setValue(null);
        view.getItemComboBox().setValue(null);
        view.getDeliveryComboBox().setValue(null);
        view.getAmountField().clear();
        view.getDeliveryPriceField().clear();
        deliveryMethods = null;
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
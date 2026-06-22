package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.DealElementDao;
import com.example.app_orderprocessing.dao.DeliveryMethodDao;
import com.example.app_orderprocessing.dao.DocumentDao;
import com.example.app_orderprocessing.dao.ItemDao;
import com.example.app_orderprocessing.model.DealElement;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.Document;
import com.example.app_orderprocessing.model.Item;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.DealElementView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.util.List;

public class DealElementController {

    private DealElementDao dealElementDao;
    private DocumentDao documentDao;
    private ItemDao itemDao;
    private DeliveryMethodDao deliveryMethodDao;

    private DealElementView dealElementView;
    private User user;

    private List<Document> documents;
    private List<Item> items;
    private List<DeliveryMethod> deliveryMethods;

    public DealElementController(
            DealElementView dealElementView,
            User user
    ) {
        this.dealElementView = dealElementView;
        this.user = user;

        dealElementDao = new DealElementDao();
        documentDao = new DocumentDao();
        itemDao = new ItemDao();
        deliveryMethodDao = new DeliveryMethodDao();

        loadDocuments();
        loadItems();
        loadDeliveryMethods();
        loadDealElements();
        checkRole();

        dealElementView.getAddButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addDealElement();
                    }
                }
        );

        dealElementView.getEditButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateDealElement();
                    }
                }
        );

        dealElementView.getDeleteButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteDealElement();
                    }
                }
        );

        dealElementView
                .getDealElementTable()
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
            dealElementView
                    .getDeleteButton()
                    .setDisable(true);
        }
    }

    private void loadDocuments() {
        documents = documentDao.getAllDocuments();

        dealElementView
                .getDocumentComboBox()
                .getItems()
                .clear();

        int i = 0;

        while (i < documents.size()) {
            Document document = documents.get(i);

            dealElementView
                    .getDocumentComboBox()
                    .getItems()
                    .add(document.getDocumentNumber());

            i++;
        }
    }

    private void loadItems() {
        items = itemDao.getAllItems();

        dealElementView
                .getItemComboBox()
                .getItems()
                .clear();

        int i = 0;

        while (i < items.size()) {
            Item item = items.get(i);

            dealElementView
                    .getItemComboBox()
                    .getItems()
                    .add(item.getItemName());

            i++;
        }
    }

    private void loadDeliveryMethods() {
        deliveryMethods =
                deliveryMethodDao.getAllDeliveryMethods();

        dealElementView
                .getDeliveryComboBox()
                .getItems()
                .clear();

        int i = 0;

        while (i < deliveryMethods.size()) {
            DeliveryMethod deliveryMethod =
                    deliveryMethods.get(i);

            dealElementView
                    .getDeliveryComboBox()
                    .getItems()
                    .add(deliveryMethod.getName());

            i++;
        }
    }

    public void loadDealElements() {
        dealElementView
                .getDealElementTable()
                .getItems()
                .setAll(
                        dealElementDao.getAllDealElements()
                );
    }

    private void addDealElement() {
        Document document = findSelectedDocument();
        Item item = findSelectedItem();

        DeliveryMethod deliveryMethod =
                findSelectedDeliveryMethod();

        if (document == null
                || item == null
                || deliveryMethod == null) {

            dealElementView.getMessageLabel().setText(
                    "Выберите документ, товар и доставку"
            );

            return;
        }

        Integer amount = readAmount();

        if (amount == null) {
            return;
        }

        BigDecimal deliveryPrice =
                readDeliveryPrice();

        if (deliveryPrice == null) {
            return;
        }

        boolean available =
                dealElementDao.isDeliveryAvailable(
                        item.getId(),
                        deliveryMethod.getId()
                );

        if (available == false) {
            dealElementView.getMessageLabel().setText(
                    "Выбранный способ доставки недоступен для товара"
            );

            return;
        }

        DealElement dealElement =
                new DealElement(
                        document.getId(),
                        item.getId(),
                        deliveryMethod.getId(),
                        amount,
                        deliveryPrice
                );

        boolean added =
                dealElementDao.addDealElement(
                        dealElement
                );

        if (added == true) {
            dealElementView.getMessageLabel().setText(
                    "Элемент сделки добавлен"
            );

            clearFields();
            loadDealElements();

        } else {
            dealElementView.getMessageLabel().setText(
                    "Не удалось добавить элемент сделки"
            );
        }
    }

    private void updateDealElement() {
        DealElement selectedDealElement =
                dealElementView
                        .getDealElementTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDealElement == null) {
            dealElementView.getMessageLabel().setText(
                    "Выберите элемент сделки"
            );

            return;
        }

        Document document = findSelectedDocument();
        Item item = findSelectedItem();

        DeliveryMethod deliveryMethod =
                findSelectedDeliveryMethod();

        if (document == null
                || item == null
                || deliveryMethod == null) {

            dealElementView.getMessageLabel().setText(
                    "Выберите документ, товар и доставку"
            );

            return;
        }

        Integer amount = readAmount();

        if (amount == null) {
            return;
        }

        BigDecimal deliveryPrice =
                readDeliveryPrice();

        if (deliveryPrice == null) {
            return;
        }

        boolean available =
                dealElementDao.isDeliveryAvailable(
                        item.getId(),
                        deliveryMethod.getId()
                );

        if (available == false) {
            dealElementView.getMessageLabel().setText(
                    "Выбранный способ доставки недоступен для товара"
            );

            return;
        }

        selectedDealElement.setDocumentId(
                document.getId()
        );

        selectedDealElement.setItemId(
                item.getId()
        );

        selectedDealElement.setDeliveryId(
                deliveryMethod.getId()
        );

        selectedDealElement.setAmount(amount);

        selectedDealElement.setDeliveryPrice(
                deliveryPrice
        );

        boolean updated =
                dealElementDao.updateDealElement(
                        selectedDealElement
                );

        if (updated == true) {
            dealElementView.getMessageLabel().setText(
                    "Элемент сделки изменён"
            );

            clearFields();
            loadDealElements();

        } else {
            dealElementView.getMessageLabel().setText(
                    "Не удалось изменить элемент сделки"
            );
        }
    }

    private void deleteDealElement() {
        if (user.getActiveRoleId() == 2) {
            dealElementView.getMessageLabel().setText(
                    "У вас нет права на удаление"
            );

            return;
        }

        DealElement selectedDealElement =
                dealElementView
                        .getDealElementTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDealElement == null) {
            dealElementView.getMessageLabel().setText(
                    "Выберите элемент сделки"
            );

            return;
        }

        boolean deleted =
                dealElementDao.deleteDealElement(
                        selectedDealElement.getId()
                );

        if (deleted == true) {
            dealElementView.getMessageLabel().setText(
                    "Элемент сделки удалён"
            );

            clearFields();
            loadDealElements();

        } else {
            dealElementView.getMessageLabel().setText(
                    "Не удалось удалить элемент сделки"
            );
        }
    }

    private Integer readAmount() {
        String amountText =
                dealElementView
                        .getAmountField()
                        .getText();

        if (amountText.isEmpty()) {
            dealElementView.getMessageLabel().setText(
                    "Введите количество"
            );

            return null;
        }

        int amount;

        try {
            amount = Integer.parseInt(amountText);

        } catch (NumberFormatException e) {
            dealElementView.getMessageLabel().setText(
                    "Количество должно быть целым числом"
            );

            return null;
        }

        if (amount <= 0) {
            dealElementView.getMessageLabel().setText(
                    "Количество должно быть больше нуля"
            );

            return null;
        }

        return amount;
    }

    private BigDecimal readDeliveryPrice() {
        String priceText =
                dealElementView
                        .getDeliveryPriceField()
                        .getText();

        if (priceText.isEmpty()) {
            dealElementView.getMessageLabel().setText(
                    "Введите стоимость доставки"
            );

            return null;
        }

        BigDecimal price;

        try {
            String correctedPrice =
                    priceText.replace(",", ".");

            price = new BigDecimal(correctedPrice);

        } catch (NumberFormatException e) {
            dealElementView.getMessageLabel().setText(
                    "Стоимость доставки должна быть числом"
            );

            return null;
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            dealElementView.getMessageLabel().setText(
                    "Стоимость доставки не может быть отрицательной"
            );

            return null;
        }

        return price;
    }

    private Document findSelectedDocument() {
        String number =
                dealElementView
                        .getDocumentComboBox()
                        .getValue();

        if (number == null) {
            return null;
        }

        int i = 0;

        while (i < documents.size()) {
            Document document = documents.get(i);

            if (document.getDocumentNumber().equals(number)) {
                return document;
            }

            i++;
        }

        return null;
    }

    private Item findSelectedItem() {
        String name =
                dealElementView
                        .getItemComboBox()
                        .getValue();

        if (name == null) {
            return null;
        }

        int i = 0;

        while (i < items.size()) {
            Item item = items.get(i);

            if (item.getItemName().equals(name)) {
                return item;
            }

            i++;
        }

        return null;
    }

    private DeliveryMethod findSelectedDeliveryMethod() {
        String name = dealElementView.getDeliveryComboBox().getValue();

        if (name == null) {
            return null;
        }

        int i = 0;

        while (i < deliveryMethods.size()) {
            DeliveryMethod deliveryMethod =
                    deliveryMethods.get(i);

            if (deliveryMethod.getName().equals(name)) {
                return deliveryMethod;
            }

            i++;
        }

        return null;
    }

    private void fillFields() {
        DealElement selectedDealElement =
                dealElementView.getDealElementTable().getSelectionModel().getSelectedItem();

        if (selectedDealElement != null) {
            dealElementView.getDocumentComboBox().setValue(selectedDealElement.getDocumentNumber());

            dealElementView.getItemComboBox().setValue(selectedDealElement.getItemName());

            dealElementView
                    .getDeliveryComboBox()
                    .setValue(
                            selectedDealElement
                                    .getDeliveryName()
                    );

            dealElementView
                    .getAmountField()
                    .setText(
                            String.valueOf(
                                    selectedDealElement
                                            .getAmount()
                            )
                    );

            dealElementView
                    .getDeliveryPriceField()
                    .setText(
                            selectedDealElement
                                    .getDeliveryPrice()
                                    .toString()
                    );
        }
    }

    private void clearFields() {
        dealElementView
                .getDocumentComboBox()
                .getSelectionModel()
                .clearSelection();

        dealElementView
                .getItemComboBox()
                .getSelectionModel()
                .clearSelection();

        dealElementView
                .getDeliveryComboBox()
                .getSelectionModel()
                .clearSelection();

        dealElementView.getAmountField().clear();
        dealElementView.getDeliveryPriceField().clear();

        dealElementView
                .getDealElementTable()
                .getSelectionModel()
                .clearSelection();
    }
}
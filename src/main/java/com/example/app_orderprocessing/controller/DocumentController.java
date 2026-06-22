package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.dao.DocumentDao;
import com.example.app_orderprocessing.model.Customer;
import com.example.app_orderprocessing.model.Document;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.view.DocumentView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.List;

public class DocumentController {

    private DocumentDao documentDao;
    private CustomerDao customerDao;

    private DocumentView documentView;
    private User user;

    private List<Customer> customers;

    public DocumentController(
            DocumentView documentView,
            User user
    ) {
        this.documentView = documentView;
        this.user = user;

        documentDao = new DocumentDao();
        customerDao = new CustomerDao();

        loadCustomers();
        loadDocuments();
        checkRole();

        documentView.getAddButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        addDocument();
                    }
                }
        );

        documentView.getEditButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateDocument();
                    }
                }
        );

        documentView.getDeleteButton().setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        deleteDocument();
                    }
                }
        );

        documentView.getDocumentTable().setOnMouseClicked(
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
            documentView
                    .getDeleteButton()
                    .setDisable(true);
        }
    }

    private void loadCustomers() {
        customers = customerDao.getAllCustomers();

        documentView.getCustomerComboBox().getItems().clear();

        int i = 0;

        while (i < customers.size()) {
            Customer customer = customers.get(i);

            documentView
                    .getCustomerComboBox()
                    .getItems()
                    .add(customer.getCustomerName());

            i++;
        }
    }

    public void loadDocuments() {
        documentView.getDocumentTable().getItems().setAll(documentDao.getAllDocuments());
    }

    private void addDocument() {
        Customer selectedCustomer =
                findSelectedCustomer();

        String documentNumber =
                documentView
                        .getDocumentNumberField()
                        .getText();

        LocalDate purchaseDate =
                documentView.getPurchaseDatePicker().getValue();

        if (selectedCustomer == null) {
            documentView.getMessageLabel().setText("Выберите заказчика");

            return;
        }

        if (documentNumber.isEmpty()) {
            documentView.getMessageLabel().setText("Введите номер документа");

            return;
        }

        if (purchaseDate == null) {
            documentView.getMessageLabel().setText("Выберите дату покупки");

            return;
        }

        if (purchaseDate.isAfter(LocalDate.now())) {
            documentView.getMessageLabel().setText("Дата покупки не может быть будущей");

            return;
        }

        Document document = new Document(
                selectedCustomer.getId(),
                documentNumber,
                purchaseDate
        );

        boolean added =
                documentDao.addDocument(document);

        if (added == true) {
            documentView.getMessageLabel().setText("Документ добавлен");

            clearFields();
            loadDocuments();

        } else {
            documentView.getMessageLabel().setText("Не удалось добавить документ. " + "Возможно, такой номер уже существует");
        }
    }

    private void updateDocument() {
        Document selectedDocument =
                documentView.getDocumentTable().getSelectionModel().getSelectedItem();

        if (selectedDocument == null) {
            documentView.getMessageLabel().setText("Выберите документ");

            return;
        }

        Customer selectedCustomer =
                findSelectedCustomer();

        String documentNumber =
                documentView.getDocumentNumberField().getText();

        LocalDate purchaseDate =
                documentView.getPurchaseDatePicker().getValue();

        if (selectedCustomer == null) {
            documentView.getMessageLabel().setText("Выберите заказчика");

            return;
        }

        if (documentNumber.isEmpty()) {
            documentView.getMessageLabel().setText("Введите номер документа");

            return;
        }

        if (purchaseDate == null) {
            documentView.getMessageLabel().setText("Выберите дату покупки");

            return;
        }

        if (purchaseDate.isAfter(LocalDate.now())) {
            documentView.getMessageLabel().setText(
                            "Дата покупки не может быть наперед"
                    );

            return;
        }

        selectedDocument.setCustomerId(
                selectedCustomer.getId()
        );

        selectedDocument.setCustomerName(
                selectedCustomer.getCustomerName()
        );

        selectedDocument.setDocumentNumber(
                documentNumber
        );

        selectedDocument.setPurchaseDate(
                purchaseDate
        );

        boolean updated =
                documentDao.updateDocument(
                        selectedDocument
                );

        if (updated == true) {
            documentView
                    .getMessageLabel()
                    .setText("Документ изменён");

            clearFields();
            loadDocuments();

        } else {
            documentView
                    .getMessageLabel()
                    .setText(
                            "Не удалось изменить документ"
                    );
        }
    }

    private void deleteDocument() {
        if (user.getActiveRoleId() == 2) {
            documentView
                    .getMessageLabel()
                    .setText(
                            "У вас нет права на удаление"
                    );

            return;
        }

        Document selectedDocument =
                documentView
                        .getDocumentTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDocument == null) {
            documentView
                    .getMessageLabel()
                    .setText("Выберите документ");

            return;
        }

        boolean deleted =
                documentDao.deleteDocument(
                        selectedDocument.getId()
                );

        if (deleted == true) {
            documentView
                    .getMessageLabel()
                    .setText("Документ удалён");

            clearFields();
            loadDocuments();

        } else {
            documentView
                    .getMessageLabel()
                    .setText(
                            "Не удалось удалить документ. " +
                                    "Возможно, в нём уже есть элементы сделки"
                    );
        }
    }

    private Customer findSelectedCustomer() {
        String selectedCustomerName =
                documentView
                        .getCustomerComboBox()
                        .getValue();

        if (selectedCustomerName == null) {
            return null;
        }

        int i = 0;

        while (i < customers.size()) {
            Customer customer = customers.get(i);

            if (customer.getCustomerName().equals(
                    selectedCustomerName
            )) {
                return customer;
            }

            i++;
        }

        return null;
    }

    private void fillFields() {
        Document selectedDocument =
                documentView
                        .getDocumentTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedDocument != null) {
            documentView
                    .getCustomerComboBox()
                    .setValue(
                            selectedDocument.getCustomerName()
                    );

            documentView
                    .getDocumentNumberField()
                    .setText(
                            selectedDocument.getDocumentNumber()
                    );

            documentView
                    .getPurchaseDatePicker()
                    .setValue(
                            selectedDocument.getPurchaseDate()
                    );
        }
    }

    private void clearFields() {
        documentView
                .getCustomerComboBox()
                .getSelectionModel()
                .clearSelection();

        documentView
                .getDocumentNumberField()
                .clear();

        documentView
                .getPurchaseDatePicker()
                .setValue(null);

        documentView
                .getDocumentTable()
                .getSelectionModel()
                .clearSelection();
    }
}
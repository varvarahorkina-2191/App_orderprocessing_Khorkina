package com.example.app_orderprocessing.controller;

import com.example.app_orderprocessing.dao.CustomerDao;
import com.example.app_orderprocessing.dao.DocumentDao;
import com.example.app_orderprocessing.dao.RoleDao;
import com.example.app_orderprocessing.model.Customer;
import com.example.app_orderprocessing.model.Document;
import com.example.app_orderprocessing.model.Role;
import com.example.app_orderprocessing.model.User;
import com.example.app_orderprocessing.utilities.ConfirmationDialog;
import com.example.app_orderprocessing.view.DocumentView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;
import java.util.List;

public class DocumentController implements EventHandler<ActionEvent> {

    private DocumentDao documentDao;
    private CustomerDao customerDao;
    private RoleDao roleDao;

    private DocumentView view;
    private User user;

    private List<Customer> customers;
    private Document selectedDocument;

    public DocumentController(DocumentView view, User user) {
        this.view = view;
        this.user = user;

        documentDao = new DocumentDao();
        customerDao = new CustomerDao();
        roleDao = new RoleDao();

        loadCustomers();
        loadDocuments();
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

        view.getDocumentTable().getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Document>() {
                    @Override
                    public void changed(ObservableValue<? extends Document> value,
                                        Document oldDocument,
                                        Document newDocument) {
                        selectDocument(newDocument);
                    }
                }
        );
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();

        if (source == view.getAddButton()) {
            saveDocument(false);
        }
        else if (source == view.getEditButton()) {
            saveDocument(true);
        }
        else if (source == view.getDeleteButton()) {
            deleteDocument();
        }
        else if (source == view.getSearchButton() || source == view.getSearchField()) {
            searchDocuments();
        }
        else if (source == view.getResetSearchButton()) {
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
            view.getCustomerComboBox().setDisable(true);
            view.getDocumentNumberField().setDisable(true);
            view.getPurchaseDatePicker().setDisable(true);

            showMessage("У вас нет доступа к документам сделок");
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

    private void loadCustomers() {
        customers = customerDao.getAllCustomers();
        view.getCustomerComboBox().getItems().clear();

        for (Customer customer : customers) {
            view.getCustomerComboBox().getItems().add(customer.getCustomerName());
        }
    }

    public void loadDocuments() {
        view.getDocumentTable().getItems().setAll(documentDao.getAllDocuments());
    }

    private void searchDocuments() {
        String text = view.getSearchField().getText().trim();

        clearFields();

        if (text.isEmpty()) {
            loadDocuments();
            showMessage("Показаны все документы");
            return;
        }

        view.getDocumentTable().getItems().setAll(documentDao.searchDocuments(text));

        int count = view.getDocumentTable().getItems().size();

        if (count == 0) {
            showMessage("Документы не найдены");
        }
        else {
            showMessage("Найдено документов: " + count);
        }
    }

    private void resetSearch() {
        view.getSearchField().clear();
        clearFields();
        loadDocuments();
        showMessage("Поиск сброшен");
    }

    private void selectDocument(Document document) {
        selectedDocument = document;

        if (document == null || isCustomer()) {
            return;
        }

        view.getCustomerComboBox().setValue(document.getCustomerName());
        view.getDocumentNumberField().setText(document.getDocumentNumber());
        view.getPurchaseDatePicker().setValue(document.getPurchaseDate());
    }

    private void saveDocument(boolean edit) {
        if (isCustomer()) {
            showMessage("У вас нет права на изменение документов");
            return;
        }

        if (edit && selectedDocument == null) {
            showMessage("Выберите документ");
            return;
        }

        Customer customer = findSelectedCustomer();
        String number = view.getDocumentNumberField().getText().trim();
        LocalDate date = view.getPurchaseDatePicker().getValue();

        if (customer == null) {
            showMessage("Выберите заказчика");
            return;
        }

        if (number.isEmpty()) {
            showMessage("Введите номер документа");
            return;
        }

        if (date == null) {
            showMessage("Выберите дату покупки");
            return;
        }

        if (date.isAfter(LocalDate.now())) {
            showMessage("Дата покупки не может быть будущей");
            return;
        }

        boolean result;

        if (edit) {
            selectedDocument.setCustomerId(customer.getId());
            selectedDocument.setCustomerName(customer.getCustomerName());
            selectedDocument.setDocumentNumber(number);
            selectedDocument.setPurchaseDate(date);

            result = documentDao.updateDocument(selectedDocument);
        }
        else {
            Document document = new Document(customer.getId(), number, date);
            result = documentDao.addDocument(document);
        }

        if (result) {
            if (edit) {
                showMessage("Документ изменён");
            }
            else {
                showMessage("Документ добавлен");
            }

            clearFields();
            view.getSearchField().clear();
            loadDocuments();
        }
        else {
            showMessage("Не удалось сохранить документ. Возможно, номер уже существует");
        }
    }

    private void deleteDocument() {
        if (isManager() || isCustomer()) {
            showMessage("У вас нет права на удаление");
            return;
        }

        if (selectedDocument == null) {
            showMessage("Выберите документ");
            return;
        }

        String text = "Удалить документ № " + selectedDocument.getDocumentNumber() + "?";
        boolean confirmed = ConfirmationDialog.show(text);

        if (confirmed == false) {
            return;
        }

        boolean deleted = documentDao.deleteDocument(selectedDocument.getId());

        if (deleted) {
            showMessage("Документ удалён");
            clearFields();
            loadDocuments();
        }
        else {
            showMessage("Не удалось удалить документ. Возможно, в нём есть элементы сделки");
        }
    }

    private Customer findSelectedCustomer() {
        String name = view.getCustomerComboBox().getValue();

        if (name == null) {
            return null;
        }

        for (Customer customer : customers) {
            if (customer.getCustomerName().equals(name)) {
                return customer;
            }
        }

        return null;
    }

    private void clearFields() {
        selectedDocument = null;
        view.getDocumentTable().getSelectionModel().clearSelection();
        view.getCustomerComboBox().setValue(null);
        view.getDocumentNumberField().clear();
        view.getPurchaseDatePicker().setValue(null);
    }

    private void showMessage(String text) {
        view.getMessageLabel().setText(text);
    }
}
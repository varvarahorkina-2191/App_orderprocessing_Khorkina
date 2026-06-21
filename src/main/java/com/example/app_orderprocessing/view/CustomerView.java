package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerView extends BorderPane {

    private TableView<Customer> customerTable;

    private TextField nameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField contactField;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public CustomerView() {
        createTable();
        createForm();
    }

    private void createTable() {
        customerTable = new TableView<>();

        TableColumn<Customer, Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Заказчик");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("customerName")
        );

        TableColumn<Customer, String> addressColumn =
                new TableColumn<>("Адрес");

        addressColumn.setCellValueFactory(
                new PropertyValueFactory<>("address")
        );

        TableColumn<Customer, String> phoneColumn =
                new TableColumn<>("Телефон");

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber")
        );

        TableColumn<Customer, String> contactColumn =
                new TableColumn<>("Контактное лицо");

        contactColumn.setCellValueFactory(
                new PropertyValueFactory<>("contactPerson")
        );

        customerTable.getColumns().add(idColumn);
        customerTable.getColumns().add(nameColumn);
        customerTable.getColumns().add(addressColumn);
        customerTable.getColumns().add(phoneColumn);
        customerTable.getColumns().add(contactColumn);

        setCenter(customerTable);
    }

    private void createForm() {
        nameField = new TextField();
        addressField = new TextField();
        phoneField = new TextField();
        contactField = new TextField();

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(new Label("Название:"), 0, 0);
        fields.add(nameField, 1, 0);

        fields.add(new Label("Адрес:"), 0, 1);
        fields.add(addressField, 1, 1);

        fields.add(new Label("Телефон:"), 2, 0);
        fields.add(phoneField, 3, 0);

        fields.add(new Label("Контактное лицо:"), 2, 1);
        fields.add(contactField, 3, 1);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(addButton);
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(deleteButton);

        VBox bottom = new VBox(10);
        bottom.getChildren().add(fields);
        bottom.getChildren().add(buttons);
        bottom.getChildren().add(messageLabel);

        bottom.setStyle("-fx-padding: 10;");

        setBottom(bottom);
    }

    public TableView<Customer> getCustomerTable() {
        return customerTable;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getAddressField() {
        return addressField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }

    public TextField getContactField() {
        return contactField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
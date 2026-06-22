package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.Customer;
import javafx.geometry.Insets;
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
    private Button profileButton;
    private Button exitButton;

    private Label messageLabel;

    public CustomerView() {
        createTop();
        createTable();
        createForm();
    }

    private void createTop() {
        profileButton = new Button("Личный кабинет");
        exitButton = new Button("Выйти");

        HBox topButtons = new HBox(10);

        topButtons.getChildren().add(profileButton);
        topButtons.getChildren().add(exitButton);

        topButtons.setPadding(new Insets(10));

        setTop(topButtons);
    }

    private void createTable() {
        customerTable = new TableView<Customer>();

        TableColumn<Customer, Integer> idColumn =
                new TableColumn<Customer, Integer>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, Integer>("id")
        );

        TableColumn<Customer, String> nameColumn =
                new TableColumn<Customer, String>("Заказчик");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>(
                        "customerName"
                )
        );

        TableColumn<Customer, String> addressColumn =
                new TableColumn<Customer, String>("Адрес");

        addressColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>(
                        "address"
                )
        );

        TableColumn<Customer, String> phoneColumn =
                new TableColumn<Customer, String>("Телефон");

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>(
                        "phoneNumber"
                )
        );

        TableColumn<Customer, String> contactColumn =
                new TableColumn<Customer, String>(
                        "Контактное лицо"
                );

        contactColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>(
                        "contactPerson"
                )
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

        bottom.setPadding(new Insets(10));

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

    public Button getProfileButton() {
        return profileButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
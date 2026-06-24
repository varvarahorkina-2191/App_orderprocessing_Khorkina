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

    private TextField searchField;
    private TextField nameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField contactField;

    private Button searchButton;
    private Button resetSearchButton;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public CustomerView() {
        createTop();
        createTable();
        createBottom();
    }

    private void createTop() {
        Label title = new Label("Управление заказчиками");

        searchField = new TextField();
        searchField.setPromptText("Введите название, адрес, телефон или контактное лицо");
        searchField.setPrefWidth(470);

        searchButton = new Button("Найти");
        resetSearchButton = new Button("Сбросить");

        HBox searchBox = new HBox(10);
        searchBox.getChildren().add(searchField);
        searchBox.getChildren().add(searchButton);
        searchBox.getChildren().add(resetSearchButton);

        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(title);
        topBox.getChildren().add(searchBox);

        setTop(topBox);
    }

    private void createTable() {
        customerTable = new TableView<Customer>();

        TableColumn<Customer, Integer> idColumn =
                new TableColumn<Customer, Integer>("ID");

        TableColumn<Customer, String> nameColumn =
                new TableColumn<Customer, String>("Заказчик");

        TableColumn<Customer, String> addressColumn =
                new TableColumn<Customer, String>("Адрес");

        TableColumn<Customer, String> phoneColumn =
                new TableColumn<Customer, String>("Телефон");

        TableColumn<Customer, String> contactColumn =
                new TableColumn<Customer, String>("Контактное лицо");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, Integer>("id")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerName")
        );

        addressColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("address")
        );

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("phoneNumber")
        );

        contactColumn.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("contactPerson")
        );

        idColumn.setPrefWidth(60);
        nameColumn.setPrefWidth(220);
        addressColumn.setPrefWidth(260);
        phoneColumn.setPrefWidth(160);
        contactColumn.setPrefWidth(220);

        customerTable.getColumns().add(idColumn);
        customerTable.getColumns().add(nameColumn);
        customerTable.getColumns().add(addressColumn);
        customerTable.getColumns().add(phoneColumn);
        customerTable.getColumns().add(contactColumn);

        customerTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        setCenter(customerTable);
    }

    private void createBottom() {
        nameField = new TextField();
        addressField = new TextField();
        phoneField = new TextField();
        contactField = new TextField();

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(new Label("Название:"), 0, 0);
        fields.add(nameField, 1, 0);

        fields.add(new Label("Телефон:"), 2, 0);
        fields.add(phoneField, 3, 0);

        fields.add(new Label("Адрес:"), 0, 1);
        fields.add(addressField, 1, 1);

        fields.add(new Label("Контактное лицо:"), 2, 1);
        fields.add(contactField, 3, 1);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(addButton);
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(deleteButton);

        VBox bottomBox = new VBox(10);
        bottomBox.setPadding(new Insets(10));
        bottomBox.getChildren().add(fields);
        bottomBox.getChildren().add(buttons);
        bottomBox.getChildren().add(messageLabel);

        setBottom(bottomBox);
    }

    public TableView<Customer> getCustomerTable() {
        return customerTable;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public Button getResetSearchButton() {
        return resetSearchButton;
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
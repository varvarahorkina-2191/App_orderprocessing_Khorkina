package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.Customer;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CustomerView extends BorderPane {

    private TableView<Customer> customerTable;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    public CustomerView() {
        createTable();
        createButtons();
    }

    private void createTable() {
        customerTable = new TableView<>();

        TableColumn<Customer, Integer> idColumn = new TableColumn<>("ID");
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

    private void createButtons() {
        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        HBox buttons = new HBox(10);

        buttons.getChildren().add(addButton);
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(deleteButton);

        setBottom(buttons);
    }

    public TableView<Customer> getCustomerTable() {
        return customerTable;
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
}
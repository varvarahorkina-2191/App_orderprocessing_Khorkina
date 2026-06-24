package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.ItemDelivery;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ItemDeliveryView extends BorderPane {

    private Label titleLabel;
    private Label messageLabel;

    private TableView<ItemDelivery> itemDeliveryTable;

    private TableColumn<ItemDelivery, Integer> itemIdColumn;
    private TableColumn<ItemDelivery, Integer> deliveryIdColumn;

    private ComboBox<String> itemComboBox;
    private ComboBox<String> deliveryComboBox;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private VBox formBox;

    public ItemDeliveryView() {
        createTop();
        createTable();
        createBottom();
    }

    private void createTop() {
        titleLabel = new Label("Управление способами доставки товаров");

        VBox topBox = new VBox(10);
        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        itemDeliveryTable = new TableView<ItemDelivery>();

        itemIdColumn =
                new TableColumn<ItemDelivery, Integer>("ID товара");

        TableColumn<ItemDelivery, String> itemNameColumn =
                new TableColumn<ItemDelivery, String>("Товар");

        deliveryIdColumn =
                new TableColumn<ItemDelivery, Integer>("ID доставки");

        TableColumn<ItemDelivery, String> deliveryNameColumn =
                new TableColumn<ItemDelivery, String>("Способ доставки");

        itemIdColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, Integer>("itemId")
        );

        itemNameColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, String>("itemName")
        );

        deliveryIdColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, Integer>("deliveryId")
        );

        deliveryNameColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, String>("deliveryName")
        );

        itemIdColumn.setPrefWidth(120);
        itemNameColumn.setPrefWidth(320);
        deliveryIdColumn.setPrefWidth(140);
        deliveryNameColumn.setPrefWidth(360);

        itemDeliveryTable.getColumns().add(itemIdColumn);
        itemDeliveryTable.getColumns().add(itemNameColumn);
        itemDeliveryTable.getColumns().add(deliveryIdColumn);
        itemDeliveryTable.getColumns().add(deliveryNameColumn);

        itemDeliveryTable.setPrefHeight(520);

        setCenter(itemDeliveryTable);
    }

    private void createBottom() {
        itemComboBox = new ComboBox<String>();
        deliveryComboBox = new ComboBox<String>();

        itemComboBox.setPromptText("Выберите товар");
        deliveryComboBox.setPromptText("Выберите способ доставки");

        itemComboBox.setPrefWidth(300);
        deliveryComboBox.setPrefWidth(300);

        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(new Label("Товар:"), 0, 0);
        fields.add(itemComboBox, 1, 0);

        fields.add(new Label("Способ доставки:"), 0, 1);
        fields.add(deliveryComboBox, 1, 1);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        addButton.setPrefWidth(120);
        editButton.setPrefWidth(120);
        deleteButton.setPrefWidth(120);

        HBox buttons = new HBox(10);
        buttons.getChildren().add(addButton);
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(deleteButton);

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        formBox = new VBox(10);
        formBox.setPadding(new Insets(15));
        formBox.getChildren().add(fields);
        formBox.getChildren().add(buttons);
        formBox.getChildren().add(messageLabel);

        setBottom(formBox);
    }

    public void enableCustomerMode() {
        titleLabel.setText("Доступные способы доставки товаров");

        itemIdColumn.setVisible(false);
        deliveryIdColumn.setVisible(false);

        formBox.setVisible(false);
        formBox.setManaged(false);

        itemDeliveryTable.setPrefHeight(650);
    }

    public TableView<ItemDelivery> getItemDeliveryTable() {
        return itemDeliveryTable;
    }

    public ComboBox<String> getItemComboBox() {
        return itemComboBox;
    }

    public ComboBox<String> getDeliveryComboBox() {
        return deliveryComboBox;
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
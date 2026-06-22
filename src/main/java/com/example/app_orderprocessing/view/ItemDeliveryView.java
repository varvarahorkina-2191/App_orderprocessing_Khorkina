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

    private TableView<ItemDelivery> itemDeliveryTable;

    private ComboBox<String> itemComboBox;
    private ComboBox<String> deliveryComboBox;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public ItemDeliveryView() {
        createTitle();
        createTable();
        createForm();
    }

    private void createTitle() {
        Label titleLabel = new Label(
                "Доступные способы доставки товаров"
        );

        VBox topBox = new VBox();

        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        itemDeliveryTable =
                new TableView<ItemDelivery>();

        TableColumn<ItemDelivery, Integer> itemIdColumn =
                new TableColumn<ItemDelivery, Integer>(
                        "ID товара"
                );

        itemIdColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, Integer>(
                        "itemId"
                )
        );

        TableColumn<ItemDelivery, String> itemNameColumn =
                new TableColumn<ItemDelivery, String>(
                        "Товар"
                );

        itemNameColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, String>(
                        "itemName"
                )
        );

        TableColumn<ItemDelivery, Integer> deliveryIdColumn =
                new TableColumn<ItemDelivery, Integer>(
                        "ID доставки"
                );

        deliveryIdColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, Integer>(
                        "deliveryId"
                )
        );

        TableColumn<ItemDelivery, String> deliveryNameColumn =
                new TableColumn<ItemDelivery, String>(
                        "Способ доставки"
                );

        deliveryNameColumn.setCellValueFactory(
                new PropertyValueFactory<ItemDelivery, String>(
                        "deliveryName"
                )
        );

        itemIdColumn.setPrefWidth(100);
        itemNameColumn.setPrefWidth(250);
        deliveryIdColumn.setPrefWidth(120);
        deliveryNameColumn.setPrefWidth(280);

        itemDeliveryTable
                .getColumns()
                .add(itemIdColumn);

        itemDeliveryTable
                .getColumns()
                .add(itemNameColumn);

        itemDeliveryTable
                .getColumns()
                .add(deliveryIdColumn);

        itemDeliveryTable
                .getColumns()
                .add(deliveryNameColumn);

        setCenter(itemDeliveryTable);
    }

    private void createForm() {
        itemComboBox = new ComboBox<String>();
        deliveryComboBox = new ComboBox<String>();

        itemComboBox.setPromptText(
                "Выберите товар"
        );

        deliveryComboBox.setPromptText(
                "Выберите способ доставки"
        );

        itemComboBox.setPrefWidth(250);
        deliveryComboBox.setPrefWidth(250);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();

        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(
                new Label("Товар:"),
                0,
                0
        );

        fields.add(
                itemComboBox,
                1,
                0
        );

        fields.add(
                new Label("Способ доставки:"),
                0,
                1
        );

        fields.add(
                deliveryComboBox,
                1,
                1
        );

        HBox buttons = new HBox(10);

        buttons.getChildren().add(addButton);
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(deleteButton);

        VBox bottomBox = new VBox(10);

        bottomBox.getChildren().add(fields);
        bottomBox.getChildren().add(buttons);
        bottomBox.getChildren().add(messageLabel);

        bottomBox.setPadding(new Insets(10));

        setBottom(bottomBox);
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
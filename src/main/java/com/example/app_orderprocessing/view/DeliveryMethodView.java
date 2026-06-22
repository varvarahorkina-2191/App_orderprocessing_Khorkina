package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.DeliveryMethod;
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

import java.math.BigDecimal;

public class DeliveryMethodView extends BorderPane {

    private TableView<DeliveryMethod> deliveryMethodTable;

    private TextField nameField;
    private TextField basicPriceField;
    private TextField deliverySpeedField;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public DeliveryMethodView() {
        createTitle();
        createTable();
        createForm();
    }

    private void createTitle() {
        Label titleLabel =
                new Label("Управление способами доставки");

        VBox topBox = new VBox();

        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        deliveryMethodTable =
                new TableView<DeliveryMethod>();

        TableColumn<DeliveryMethod, Integer> idColumn =
                new TableColumn<DeliveryMethod, Integer>(
                        "ID"
                );

        idColumn.setCellValueFactory(
                new PropertyValueFactory<DeliveryMethod, Integer>(
                        "id"
                )
        );

        TableColumn<DeliveryMethod, String> nameColumn =
                new TableColumn<DeliveryMethod, String>(
                        "Способ доставки"
                );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<DeliveryMethod, String>(
                        "name"
                )
        );

        TableColumn<DeliveryMethod, BigDecimal> priceColumn =
                new TableColumn<DeliveryMethod, BigDecimal>(
                        "Базовая стоимость"
                );

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<DeliveryMethod, BigDecimal>(
                        "basicPrice"
                )
        );

        TableColumn<DeliveryMethod, String> speedColumn =
                new TableColumn<DeliveryMethod, String>(
                        "Скорость доставки"
                );

        speedColumn.setCellValueFactory(
                new PropertyValueFactory<DeliveryMethod, String>(
                        "deliverySpeed"
                )
        );

        idColumn.setPrefWidth(60);
        nameColumn.setPrefWidth(230);
        priceColumn.setPrefWidth(170);
        speedColumn.setPrefWidth(230);

        deliveryMethodTable
                .getColumns()
                .add(idColumn);

        deliveryMethodTable
                .getColumns()
                .add(nameColumn);

        deliveryMethodTable
                .getColumns()
                .add(priceColumn);

        deliveryMethodTable
                .getColumns()
                .add(speedColumn);

        setCenter(deliveryMethodTable);
    }

    private void createForm() {
        nameField = new TextField();
        basicPriceField = new TextField();
        deliverySpeedField = new TextField();

        nameField.setPromptText(
                "Например: Курьерская доставка"
        );

        basicPriceField.setPromptText(
                "Например: 500"
        );

        deliverySpeedField.setPromptText(
                "Например: 1–3 дня"
        );

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();

        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(
                new Label("Название:"),
                0,
                0
        );

        fields.add(
                nameField,
                1,
                0
        );

        fields.add(
                new Label("Базовая стоимость:"),
                2,
                0
        );

        fields.add(
                basicPriceField,
                3,
                0
        );

        fields.add(
                new Label("Скорость доставки:"),
                0,
                1
        );

        fields.add(
                deliverySpeedField,
                1,
                1,
                3,
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

    public TableView<DeliveryMethod> getDeliveryMethodTable() {
        return deliveryMethodTable;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getBasicPriceField() {
        return basicPriceField;
    }

    public TextField getDeliverySpeedField() {
        return deliverySpeedField;
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
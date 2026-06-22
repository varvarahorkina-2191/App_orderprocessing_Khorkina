package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.DealElement;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class DealElementView extends BorderPane {

    private TableView<DealElement> dealElementTable;

    private ComboBox<String> documentComboBox;
    private ComboBox<String> itemComboBox;
    private ComboBox<String> deliveryComboBox;

    private TextField amountField;
    private TextField deliveryPriceField;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public DealElementView() {
        createTitle();
        createTable();
        createForm();
    }

    private void createTitle() {
        Label titleLabel =
                new Label("Управление элементами сделок");

        VBox topBox = new VBox();

        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        dealElementTable =
                new TableView<DealElement>();

        TableColumn<DealElement, Integer> idColumn =
                new TableColumn<DealElement, Integer>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, Integer>(
                        "id"
                )
        );

        TableColumn<DealElement, String> documentColumn =
                new TableColumn<DealElement, String>(
                        "Документ"
                );

        documentColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>(
                        "documentNumber"
                )
        );

        TableColumn<DealElement, String> itemColumn =
                new TableColumn<DealElement, String>(
                        "Товар"
                );

        itemColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>(
                        "itemName"
                )
        );

        TableColumn<DealElement, String> deliveryColumn =
                new TableColumn<DealElement, String>(
                        "Способ доставки"
                );

        deliveryColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>(
                        "deliveryName"
                )
        );

        TableColumn<DealElement, Integer> amountColumn =
                new TableColumn<DealElement, Integer>(
                        "Количество"
                );

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, Integer>(
                        "amount"
                )
        );

        TableColumn<DealElement, BigDecimal> deliveryPriceColumn =
                new TableColumn<DealElement, BigDecimal>(
                        "Стоимость доставки"
                );

        deliveryPriceColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, BigDecimal>(
                        "deliveryPrice"
                )
        );

        idColumn.setPrefWidth(60);
        documentColumn.setPrefWidth(150);
        itemColumn.setPrefWidth(190);
        deliveryColumn.setPrefWidth(200);
        amountColumn.setPrefWidth(110);
        deliveryPriceColumn.setPrefWidth(170);

        dealElementTable.getColumns().add(idColumn);
        dealElementTable.getColumns().add(documentColumn);
        dealElementTable.getColumns().add(itemColumn);
        dealElementTable.getColumns().add(deliveryColumn);
        dealElementTable.getColumns().add(amountColumn);
        dealElementTable.getColumns().add(deliveryPriceColumn);

        setCenter(dealElementTable);
    }

    private void createForm() {
        documentComboBox = new ComboBox<String>();
        itemComboBox = new ComboBox<String>();
        deliveryComboBox = new ComboBox<String>();

        amountField = new TextField();
        deliveryPriceField = new TextField();

        documentComboBox.setPromptText(
                "Выберите документ"
        );

        itemComboBox.setPromptText(
                "Выберите товар"
        );

        deliveryComboBox.setPromptText(
                "Выберите способ доставки"
        );

        amountField.setPromptText(
                "Например: 5"
        );

        deliveryPriceField.setPromptText(
                "Например: 500"
        );

        documentComboBox.setPrefWidth(220);
        itemComboBox.setPrefWidth(220);
        deliveryComboBox.setPrefWidth(220);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();

        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(
                new Label("Документ:"),
                0,
                0
        );

        fields.add(
                documentComboBox,
                1,
                0
        );

        fields.add(
                new Label("Товар:"),
                2,
                0
        );

        fields.add(
                itemComboBox,
                3,
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

        fields.add(
                new Label("Количество:"),
                2,
                1
        );

        fields.add(
                amountField,
                3,
                1
        );

        fields.add(
                new Label("Стоимость доставки:"),
                0,
                2
        );

        fields.add(
                deliveryPriceField,
                1,
                2
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

    public TableView<DealElement> getDealElementTable() {
        return dealElementTable;
    }

    public ComboBox<String> getDocumentComboBox() {
        return documentComboBox;
    }

    public ComboBox<String> getItemComboBox() {
        return itemComboBox;
    }

    public ComboBox<String> getDeliveryComboBox() {
        return deliveryComboBox;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public TextField getDeliveryPriceField() {
        return deliveryPriceField;
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
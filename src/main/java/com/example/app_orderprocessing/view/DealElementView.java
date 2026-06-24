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

    private TextField searchField;
    private TextField amountField;
    private TextField deliveryPriceField;

    private ComboBox<String> documentComboBox;
    private ComboBox<String> itemComboBox;
    private ComboBox<String> deliveryComboBox;

    private Button searchButton;
    private Button resetSearchButton;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public DealElementView() {
        createTop();
        createTable();
        createBottom();
    }

    private void createTop() {
        Label title = new Label("Управление элементами сделки");

        searchField = new TextField();
        searchField.setPromptText("Введите номер документа, товар или способ доставки");
        searchField.setPrefWidth(470);

        searchButton = new Button("Найти");
        resetSearchButton = new Button("Сбросить");

        searchButton.setPrefWidth(100);
        resetSearchButton.setPrefWidth(100);

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
        dealElementTable = new TableView<DealElement>();

        TableColumn<DealElement, Integer> idColumn =
                new TableColumn<DealElement, Integer>("ID");

        TableColumn<DealElement, String> documentColumn =
                new TableColumn<DealElement, String>("Номер документа");

        TableColumn<DealElement, String> itemColumn =
                new TableColumn<DealElement, String>("Товар");

        TableColumn<DealElement, String> deliveryColumn =
                new TableColumn<DealElement, String>("Способ доставки");

        TableColumn<DealElement, Integer> amountColumn =
                new TableColumn<DealElement, Integer>("Количество");

        TableColumn<DealElement, BigDecimal> deliveryPriceColumn =
                new TableColumn<DealElement, BigDecimal>(
                        "Стоимость доставки"
                );

        idColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, Integer>("id")
        );

        documentColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>(
                        "documentNumber"
                )
        );

        itemColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>("itemName")
        );

        deliveryColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, String>("deliveryName")
        );

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, Integer>("amount")
        );

        deliveryPriceColumn.setCellValueFactory(
                new PropertyValueFactory<DealElement, BigDecimal>(
                        "deliveryPrice"
                )
        );

        idColumn.setPrefWidth(60);
        documentColumn.setPrefWidth(160);
        itemColumn.setPrefWidth(280);
        deliveryColumn.setPrefWidth(250);
        amountColumn.setPrefWidth(120);
        deliveryPriceColumn.setPrefWidth(180);

        dealElementTable.getColumns().add(idColumn);
        dealElementTable.getColumns().add(documentColumn);
        dealElementTable.getColumns().add(itemColumn);
        dealElementTable.getColumns().add(deliveryColumn);
        dealElementTable.getColumns().add(amountColumn);
        dealElementTable.getColumns().add(deliveryPriceColumn);

        setCenter(dealElementTable);
    }

    private void createBottom() {
        documentComboBox = new ComboBox<String>();
        itemComboBox = new ComboBox<String>();
        deliveryComboBox = new ComboBox<String>();

        amountField = new TextField();
        deliveryPriceField = new TextField();

        documentComboBox.setPromptText("Выберите документ");
        itemComboBox.setPromptText("Выберите товар");
        deliveryComboBox.setPromptText("Выберите способ доставки");

        amountField.setPromptText("Введите количество");
        deliveryPriceField.setPromptText(
                "Стоимость подставится автоматически"
        );

        documentComboBox.setPrefWidth(230);
        itemComboBox.setPrefWidth(300);
        deliveryComboBox.setPrefWidth(270);
        amountField.setPrefWidth(150);
        deliveryPriceField.setPrefWidth(220);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();
        messageLabel.setWrapText(true);

        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(new Label("Документ:"), 0, 0);
        fields.add(documentComboBox, 1, 0);

        fields.add(new Label("Товар:"), 2, 0);
        fields.add(itemComboBox, 3, 0);

        fields.add(new Label("Способ доставки:"), 0, 1);
        fields.add(deliveryComboBox, 1, 1);

        fields.add(new Label("Количество:"), 2, 1);
        fields.add(amountField, 3, 1);

        fields.add(new Label("Стоимость доставки:"), 0, 2);
        fields.add(deliveryPriceField, 1, 2);

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

    public TableView<DealElement> getDealElementTable() {
        return dealElementTable;
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
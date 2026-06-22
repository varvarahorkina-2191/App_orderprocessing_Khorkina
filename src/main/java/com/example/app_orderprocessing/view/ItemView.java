package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.Item;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class ItemView extends BorderPane {

    private TableView<Item> itemTable;

    private TextField nameField;
    private TextField priceField;
    private TextArea informationArea;
    private CheckBox deliveryCheckBox;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public ItemView() {
        createTitle();
        createTable();
        createForm();
    }

    private void createTitle() {
        Label titleLabel = new Label("Управление товарами");

        VBox topBox = new VBox();
        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        itemTable = new TableView<Item>();

        TableColumn<Item, Integer> idColumn =
                new TableColumn<Item, Integer>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Item, Integer>("id")
        );

        TableColumn<Item, String> nameColumn =
                new TableColumn<Item, String>("Название");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>(
                        "itemName"
                )
        );

        TableColumn<Item, BigDecimal> priceColumn =
                new TableColumn<Item, BigDecimal>("Цена");

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<Item, BigDecimal>(
                        "price"
                )
        );

        TableColumn<Item, String> informationColumn =
                new TableColumn<Item, String>("Информация");

        informationColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>(
                        "itemInformation"
                )
        );

        TableColumn<Item, Boolean> deliveryColumn =
                new TableColumn<Item, Boolean>("Есть доставка");

        deliveryColumn.setCellValueFactory(
                new PropertyValueFactory<Item, Boolean>(
                        "hasDelivery"
                )
        );

        idColumn.setPrefWidth(50);
        nameColumn.setPrefWidth(180);
        priceColumn.setPrefWidth(100);
        informationColumn.setPrefWidth(300);
        deliveryColumn.setPrefWidth(120);

        itemTable.getColumns().add(idColumn);
        itemTable.getColumns().add(nameColumn);
        itemTable.getColumns().add(priceColumn);
        itemTable.getColumns().add(informationColumn);
        itemTable.getColumns().add(deliveryColumn);

        setCenter(itemTable);
    }

    private void createForm() {
        nameField = new TextField();
        priceField = new TextField();

        informationArea = new TextArea();
        informationArea.setPrefRowCount(3);
        informationArea.setWrapText(true);

        deliveryCheckBox =
                new CheckBox("Для товара доступна доставка");

        deliveryCheckBox.setSelected(true);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();
        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(
                new Label("Название товара:"),
                0,
                0
        );

        fields.add(
                nameField,
                1,
                0
        );

        fields.add(
                new Label("Цена:"),
                2,
                0
        );

        fields.add(
                priceField,
                3,
                0
        );

        fields.add(
                new Label("Информация:"),
                0,
                1
        );

        fields.add(
                informationArea,
                1,
                1,
                3,
                1
        );

        fields.add(
                deliveryCheckBox,
                1,
                2,
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

    public TableView<Item> getItemTable() {
        return itemTable;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public TextArea getInformationArea() {
        return informationArea;
    }

    public CheckBox getDeliveryCheckBox() {
        return deliveryCheckBox;
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
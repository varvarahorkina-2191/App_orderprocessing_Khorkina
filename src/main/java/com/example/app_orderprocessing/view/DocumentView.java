package com.example.app_orderprocessing.view;

import com.example.app_orderprocessing.model.Document;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class DocumentView extends BorderPane {

    private TableView<Document> documentTable;

    private ComboBox<String> customerComboBox;
    private TextField documentNumberField;
    private DatePicker purchaseDatePicker;

    private Button addButton;
    private Button editButton;
    private Button deleteButton;

    private Label messageLabel;

    public DocumentView() {
        createTitle();
        createTable();
        createForm();
    }

    private void createTitle() {
        Label titleLabel =
                new Label("Управление документами сделок");

        VBox topBox = new VBox();

        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(titleLabel);

        setTop(topBox);
    }

    private void createTable() {
        documentTable = new TableView<Document>();

        TableColumn<Document, Integer> idColumn =
                new TableColumn<Document, Integer>("ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Document, Integer>(
                        "id"
                )
        );

        TableColumn<Document, Integer> customerIdColumn =
                new TableColumn<Document, Integer>(
                        "ID заказчика"
                );

        customerIdColumn.setCellValueFactory(
                new PropertyValueFactory<Document, Integer>(
                        "customerId"
                )
        );

        TableColumn<Document, String> customerNameColumn =
                new TableColumn<Document, String>(
                        "Заказчик"
                );

        customerNameColumn.setCellValueFactory(
                new PropertyValueFactory<Document, String>(
                        "customerName"
                )
        );

        TableColumn<Document, String> numberColumn =
                new TableColumn<Document, String>(
                        "Номер документа"
                );

        numberColumn.setCellValueFactory(
                new PropertyValueFactory<Document, String>(
                        "documentNumber"
                )
        );

        TableColumn<Document, LocalDate> dateColumn =
                new TableColumn<Document, LocalDate>(
                        "Дата покупки"
                );

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<Document, LocalDate>(
                        "purchaseDate"
                )
        );

        idColumn.setPrefWidth(60);
        customerIdColumn.setPrefWidth(120);
        customerNameColumn.setPrefWidth(240);
        numberColumn.setPrefWidth(180);
        dateColumn.setPrefWidth(150);

        documentTable.getColumns().add(idColumn);
        documentTable.getColumns().add(customerIdColumn);
        documentTable.getColumns().add(customerNameColumn);
        documentTable.getColumns().add(numberColumn);
        documentTable.getColumns().add(dateColumn);

        setCenter(documentTable);
    }

    private void createForm() {
        customerComboBox = new ComboBox<String>();
        documentNumberField = new TextField();
        purchaseDatePicker = new DatePicker();

        customerComboBox.setPromptText(
                "Выберите заказчика"
        );

        documentNumberField.setPromptText(
                "Введите номер документа"
        );

        purchaseDatePicker.setPromptText(
                "Выберите дату"
        );

        customerComboBox.setPrefWidth(250);
        documentNumberField.setPrefWidth(200);
        purchaseDatePicker.setPrefWidth(180);

        addButton = new Button("Добавить");
        editButton = new Button("Изменить");
        deleteButton = new Button("Удалить");

        messageLabel = new Label();

        GridPane fields = new GridPane();

        fields.setHgap(10);
        fields.setVgap(10);

        fields.add(
                new Label("Заказчик:"),
                0,
                0
        );

        fields.add(
                customerComboBox,
                1,
                0
        );

        fields.add(
                new Label("Номер документа:"),
                0,
                1
        );

        fields.add(
                documentNumberField,
                1,
                1
        );

        fields.add(
                new Label("Дата покупки:"),
                2,
                1
        );

        fields.add(
                purchaseDatePicker,
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

    public TableView<Document> getDocumentTable() {
        return documentTable;
    }

    public ComboBox<String> getCustomerComboBox() {
        return customerComboBox;
    }

    public TextField getDocumentNumberField() {
        return documentNumberField;
    }

    public DatePicker getPurchaseDatePicker() {
        return purchaseDatePicker;
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
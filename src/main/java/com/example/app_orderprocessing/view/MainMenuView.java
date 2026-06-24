package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuView extends BorderPane {

    private Label userLabel;
    private Label roleLabel;
    private Label sectionTitleLabel;

    private Button usersButton;
    private Button customersButton;
    private Button itemsButton;
    private Button deliveryMethodsButton;
    private Button itemDeliveryButton;
    private Button documentsButton;
    private Button dealElementsButton;
    private Button personalAccountButton;
    private Button exitButton;

    private BorderPane contentPane;

    public MainMenuView() {
        createMenu();
        createContent();
        showWelcome();
    }

    private void createMenu() {
        Label systemLabel = new Label("Система управления заказами");
        systemLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        systemLabel.setWrapText(true);
        systemLabel.setMaxWidth(220);
        systemLabel.setAlignment(Pos.CENTER);

        userLabel = new Label();
        roleLabel = new Label();

        userLabel.setFont(Font.font("Arial", 14));
        roleLabel.setFont(Font.font("Arial", 14));

        usersButton = createButton("Пользователи");
        customersButton = createButton("Заказчики");
        itemsButton = createButton("Товары");
        deliveryMethodsButton = createButton("Способы доставки");
        itemDeliveryButton = createButton("Товары и доставка");
        documentsButton = createButton("Документы");
        dealElementsButton = createButton("Элементы сделок");
        personalAccountButton = createButton("Личный кабинет");
        exitButton = createButton("Выйти");

        VBox emptySpace = new VBox();
        VBox.setVgrow(emptySpace, Priority.ALWAYS);

        VBox menu = new VBox(12);
        menu.setPadding(new Insets(20, 15, 20, 15));
        menu.setPrefWidth(250);
        menu.setMinWidth(250);
        menu.setAlignment(Pos.TOP_CENTER);

        VBox.setMargin(usersButton, new Insets(25, 0, 0, 0));

        menu.getChildren().add(systemLabel);
        menu.getChildren().add(userLabel);
        menu.getChildren().add(roleLabel);
        menu.getChildren().add(usersButton);
        menu.getChildren().add(customersButton);
        menu.getChildren().add(itemsButton);
        menu.getChildren().add(deliveryMethodsButton);
        menu.getChildren().add(itemDeliveryButton);
        menu.getChildren().add(documentsButton);
        menu.getChildren().add(dealElementsButton);
        menu.getChildren().add(personalAccountButton);
        menu.getChildren().add(emptySpace);
        menu.getChildren().add(exitButton);

        setLeft(menu);
    }

    private Button createButton(String text) {
        Button button = new Button(text);

        button.setPrefWidth(220);
        button.setMinHeight(32);
        button.setFont(Font.font("Arial", 14));

        return button;
    }

    private void createContent() {
        sectionTitleLabel = new Label("Главная");
        sectionTitleLabel.setFont(
                Font.font("Arial", FontWeight.BOLD, 18)
        );

        contentPane = new BorderPane();
        contentPane.setPadding(new Insets(20));

        VBox center = new VBox(10);
        center.setPadding(new Insets(20));
        center.getChildren().add(sectionTitleLabel);
        center.getChildren().add(contentPane);

        VBox.setVgrow(contentPane, Priority.ALWAYS);

        setCenter(center);
    }

    public void showWelcome() {
        sectionTitleLabel.setText("Главная");

        Label title = new Label(
                "Добро пожаловать в систему управления заказами!"
        );

        title.setFont(
                Font.font("Arial", FontWeight.BOLD, 22)
        );

        Label text = new Label(
                "Система предназначена для работы "
                        + "с заказчиками, товарами, способами доставки "
                        + "и документами сделок.\n\n"
                        + "Для начала работы выберите необходимый "
                        + "раздел в меню слева."
        );

        text.setWrapText(true);
        text.setMaxWidth(650);
        text.setFont(Font.font("Arial", 15));

        VBox welcome = new VBox(20);
        welcome.getChildren().add(title);
        welcome.getChildren().add(text);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(40));

        contentPane.setCenter(welcome);
    }

    public void showContent(String title, Parent content) {
        sectionTitleLabel.setText(title);
        contentPane.setCenter(content);
    }

    public Label getUserLabel() {
        return userLabel;
    }

    public Label getRoleLabel() {
        return roleLabel;
    }

    public Button getUsersButton() {
        return usersButton;
    }

    public Button getCustomersButton() {
        return customersButton;
    }

    public Button getItemsButton() {
        return itemsButton;
    }

    public Button getDeliveryMethodsButton() {
        return deliveryMethodsButton;
    }

    public Button getItemDeliveryButton() {
        return itemDeliveryButton;
    }

    public Button getDocumentsButton() {
        return documentsButton;
    }

    public Button getDealElementsButton() {
        return dealElementsButton;
    }

    public Button getPersonalAccountButton() {
        return personalAccountButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
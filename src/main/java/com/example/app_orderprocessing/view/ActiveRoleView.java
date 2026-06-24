package com.example.app_orderprocessing.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ActiveRoleView extends VBox {

    private ComboBox<String> roleComboBox;
    private Button continueButton;
    private Label messageLabel;

    public ActiveRoleView() {
        Label titleLabel = new Label("Выбор активной роли");
        Label roleLabel = new Label("Выберите роль для работы:");

        roleComboBox = new ComboBox<String>();
        roleComboBox.setPromptText("Выберите роль");
        roleComboBox.setPrefWidth(240);

        continueButton = new Button("Продолжить");
        continueButton.setPrefWidth(160);

        messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(280);

        setSpacing(12);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);

        getChildren().add(titleLabel);
        getChildren().add(roleLabel);
        getChildren().add(roleComboBox);
        getChildren().add(continueButton);
        getChildren().add(messageLabel);
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }
}
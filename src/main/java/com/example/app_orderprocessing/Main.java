package com.example.app_orderprocessing;

import com.example.app_orderprocessing.controller.CustomerController;
import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.view.CustomerView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DatabaseConnection.getInstance();

        CustomerView customerView = new CustomerView();
        new CustomerController(customerView);

        Scene scene = new Scene(customerView, 900, 500);

        stage.setTitle("Заказчики");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
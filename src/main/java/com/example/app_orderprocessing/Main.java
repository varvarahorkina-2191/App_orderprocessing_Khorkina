package com.example.app_orderprocessing;

import com.example.app_orderprocessing.controller.LoginController;
import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseConnection.getInstance();

        LoginView loginView = new LoginView();

        new LoginController(
                loginView,
                stage
        );

        Scene scene = new Scene(
                loginView,
                400,
                300
        );

        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
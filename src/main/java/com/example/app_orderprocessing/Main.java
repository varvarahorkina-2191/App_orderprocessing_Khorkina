package com.example.app_orderprocessing;

import com.example.app_orderprocessing.controller.RegistrationController;
import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.view.RegistrationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseConnection.getInstance();

        RegistrationView view = new RegistrationView();
        new RegistrationController(view);

        Scene scene = new Scene(view, 400, 350);

        stage.setTitle("Регистрация");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
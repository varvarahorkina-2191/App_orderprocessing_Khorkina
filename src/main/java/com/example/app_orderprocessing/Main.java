package com.example.app_orderprocessing;

import com.example.app_orderprocessing.controller.LoginController;
import com.example.app_orderprocessing.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        LoginView view = new LoginView();
        new LoginController(view, stage);

        stage.setTitle("Авторизация");
        stage.setScene(new Scene(view, 400, 300));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
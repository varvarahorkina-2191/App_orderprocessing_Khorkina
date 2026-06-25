package com.example.app_orderprocessing.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        connect();
    }

    private void connect() {
        try {
            Properties properties = new Properties();

            FileInputStream file =
                    new FileInputStream("config/database.properties");

            properties.load(file);
            file.close();

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(
                    url,
                    user,
                    password
            );

            System.out.println("Подключение к базе данных выполнено");
        } catch (Exception e) {
            System.out.println(
                    "Ошибка подключения к базе данных: "
                            + e.getMessage()
            );
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
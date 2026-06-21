package com.example.app_orderprocessing.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;


    private static final String URL =
            "jdbc:mysql://localhost:3306/order_processing ";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("База данных подключена");
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных");
            e.printStackTrace();
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
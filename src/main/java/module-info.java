module com.example.app_orderprocessing {
    requires javafx.controls;
    requires java.sql;
    requires jbcrypt;

    exports com.example.app_orderprocessing;
    exports com.example.app_orderprocessing.database;
    exports com.example.app_orderprocessing.model;
    exports com.example.app_orderprocessing.view;
    exports com.example.app_orderprocessing.controller;
    exports com.example.app_orderprocessing.dao;
    exports com.example.app_orderprocessing.utilities;

    opens com.example.app_orderprocessing.model to javafx.base;
}
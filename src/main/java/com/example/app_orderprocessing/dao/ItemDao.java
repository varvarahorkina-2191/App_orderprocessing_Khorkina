package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDao {

    private Connection connection;

    public ItemDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<Item>();

        String sql = """
                SELECT id,
                       item_name,
                       price,
                       item_information,
                       has_delivery
                FROM items
                ORDER BY id
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String itemName =
                        result.getString("item_name");

                BigDecimal price =
                        result.getBigDecimal("price");

                String itemInformation =
                        result.getString("item_information");

                boolean hasDelivery =
                        result.getBoolean("has_delivery");

                Item item = new Item(
                        id,
                        itemName,
                        price,
                        itemInformation,
                        hasDelivery
                );

                items.add(item);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении списка товаров"
            );

            e.printStackTrace();
        }

        return items;
    }

    public boolean addItem(Item item) {
        String sql = """
                INSERT INTO items
                (item_name, price, item_information, has_delivery)
                VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setString(
                    1,
                    item.getItemName()
            );

            statement.setBigDecimal(
                    2,
                    item.getPrice()
            );

            statement.setString(
                    3,
                    item.getItemInformation()
            );

            statement.setBoolean(
                    4,
                    item.getHasDelivery()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при добавлении товара"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateItem(Item item) {
        String sql = """
                UPDATE items
                SET item_name = ?,
                    price = ?,
                    item_information = ?,
                    has_delivery = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setString(
                    1,
                    item.getItemName()
            );

            statement.setBigDecimal(
                    2,
                    item.getPrice()
            );

            statement.setString(
                    3,
                    item.getItemInformation()
            );

            statement.setBoolean(
                    4,
                    item.getHasDelivery()
            );

            statement.setInt(
                    5,
                    item.getId()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении товара"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteItem(int itemId) {
        String sql = """
                DELETE FROM items
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(1, itemId);

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при удалении товара"
            );

            e.printStackTrace();
        }

        return false;
    }
}
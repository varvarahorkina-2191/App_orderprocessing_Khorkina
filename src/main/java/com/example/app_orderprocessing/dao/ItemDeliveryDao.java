package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.ItemDelivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDeliveryDao {

    private Connection connection;

    public ItemDeliveryDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<ItemDelivery> getAllItemDeliveries() {
        ArrayList<ItemDelivery> itemDeliveries =
                new ArrayList<ItemDelivery>();

        String sql = """
                SELECT item_delivery.item_id,
                       item_delivery.delivery_id,
                       items.item_name,
                       delivery_methods.name AS delivery_name
                FROM item_delivery
                INNER JOIN items
                    ON item_delivery.item_id = items.id
                INNER JOIN delivery_methods
                    ON item_delivery.delivery_id = delivery_methods.id
                ORDER BY items.item_name,
                         delivery_methods.name
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int itemId =
                        result.getInt("item_id");

                int deliveryId =
                        result.getInt("delivery_id");

                String itemName =
                        result.getString("item_name");

                String deliveryName =
                        result.getString("delivery_name");

                ItemDelivery itemDelivery =
                        new ItemDelivery(
                                itemId,
                                deliveryId,
                                itemName,
                                deliveryName
                        );

                itemDeliveries.add(itemDelivery);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении способов доставки товаров"
            );

            e.printStackTrace();
        }

        return itemDeliveries;
    }

    public boolean addItemDelivery(
            ItemDelivery itemDelivery
    ) {
        String sql = """
                INSERT INTO item_delivery
                (item_id, delivery_id)
                VALUES (?, ?)
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    itemDelivery.getItemId()
            );

            statement.setInt(
                    2,
                    itemDelivery.getDeliveryId()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при назначении способа доставки товару"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateItemDelivery(
            ItemDelivery itemDelivery,
            int oldItemId,
            int oldDeliveryId
    ) {
        String sql = """
                UPDATE item_delivery
                SET item_id = ?,
                    delivery_id = ?
                WHERE item_id = ?
                  AND delivery_id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    itemDelivery.getItemId()
            );

            statement.setInt(
                    2,
                    itemDelivery.getDeliveryId()
            );

            statement.setInt(
                    3,
                    oldItemId
            );

            statement.setInt(
                    4,
                    oldDeliveryId
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении способа доставки товара"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteItemDelivery(
            int itemId,
            int deliveryId
    ) {
        String sql = """
                DELETE FROM item_delivery
                WHERE item_id = ?
                  AND delivery_id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    itemId
            );

            statement.setInt(
                    2,
                    deliveryId
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при удалении способа доставки товара"
            );

            e.printStackTrace();
        }

        return false;
    }
}
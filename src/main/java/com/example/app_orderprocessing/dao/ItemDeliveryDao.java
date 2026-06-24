package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.DeliveryMethod;
import com.example.app_orderprocessing.model.ItemDelivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDeliveryDao {

    private Connection connection;

    public ItemDeliveryDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public ArrayList<ItemDelivery> getAllItemDeliveries() {
        ArrayList<ItemDelivery> itemDeliveries = new ArrayList<ItemDelivery>();

        String sql = """
                SELECT item_delivery.item_id, item_delivery.delivery_id,
                       items.item_name, delivery_methods.name AS delivery_name
                FROM item_delivery
                JOIN items ON item_delivery.item_id = items.id
                JOIN delivery_methods ON item_delivery.delivery_id = delivery_methods.id
                ORDER BY items.item_name, delivery_methods.name
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                ItemDelivery itemDelivery = new ItemDelivery(
                        result.getInt("item_id"),
                        result.getInt("delivery_id"),
                        result.getString("item_name"),
                        result.getString("delivery_name")
                );

                itemDeliveries.add(itemDelivery);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении способов доставки товаров");
            e.printStackTrace();
        }

        return itemDeliveries;
    }

    public ArrayList<DeliveryMethod> getDeliveryMethodsByItemId(int itemId) {
        ArrayList<DeliveryMethod> deliveryMethods = new ArrayList<DeliveryMethod>();

        String sql = """
                SELECT delivery_methods.id, delivery_methods.name,
                       delivery_methods.basic_price, delivery_methods.delivery_speed
                FROM delivery_methods
                JOIN item_delivery ON delivery_methods.id = item_delivery.delivery_id
                WHERE item_delivery.item_id = ?
                ORDER BY delivery_methods.name
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                DeliveryMethod deliveryMethod = new DeliveryMethod(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getBigDecimal("basic_price"),
                        result.getString("delivery_speed")
                );

                deliveryMethods.add(deliveryMethod);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении способов доставки выбранного товара");
            e.printStackTrace();
        }

        return deliveryMethods;
    }

    public boolean addItemDelivery(ItemDelivery itemDelivery) {
        String sql = """
                INSERT INTO item_delivery (item_id, delivery_id)
                VALUES (?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, itemDelivery.getItemId());
            statement.setInt(2, itemDelivery.getDeliveryId());

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при назначении способа доставки товару");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateItemDelivery(ItemDelivery itemDelivery, int oldItemId, int oldDeliveryId) {
        String sql = """
                UPDATE item_delivery
                SET item_id = ?, delivery_id = ?
                WHERE item_id = ? AND delivery_id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, itemDelivery.getItemId());
            statement.setInt(2, itemDelivery.getDeliveryId());
            statement.setInt(3, oldItemId);
            statement.setInt(4, oldDeliveryId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении способа доставки товара");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteItemDelivery(int itemId, int deliveryId) {
        String sql = """
                DELETE FROM item_delivery
                WHERE item_id = ? AND delivery_id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, itemId);
            statement.setInt(2, deliveryId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении способа доставки товара");
            e.printStackTrace();
        }

        return false;
    }
}
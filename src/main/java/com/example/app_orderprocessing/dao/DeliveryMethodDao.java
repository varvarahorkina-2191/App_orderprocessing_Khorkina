package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.DeliveryMethod;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryMethodDao {

    private Connection connection;

    public DeliveryMethodDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<DeliveryMethod> getAllDeliveryMethods() {
        ArrayList<DeliveryMethod> deliveryMethods =
                new ArrayList<DeliveryMethod>();

        String sql = """
                SELECT id,
                       name,
                       basic_price,
                       delivery_speed
                FROM delivery_methods
                ORDER BY id
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");

                String name =
                        result.getString("name");

                BigDecimal basicPrice =
                        result.getBigDecimal("basic_price");

                String deliverySpeed =
                        result.getString("delivery_speed");

                DeliveryMethod deliveryMethod =
                        new DeliveryMethod(
                                id,
                                name,
                                basicPrice,
                                deliverySpeed
                        );

                deliveryMethods.add(deliveryMethod);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении способов доставки"
            );

            e.printStackTrace();
        }

        return deliveryMethods;
    }

    public boolean addDeliveryMethod(
            DeliveryMethod deliveryMethod
    ) {
        String sql = """
                INSERT INTO delivery_methods
                (name, basic_price, delivery_speed)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setString(
                    1,
                    deliveryMethod.getName()
            );

            statement.setBigDecimal(
                    2,
                    deliveryMethod.getBasicPrice()
            );

            statement.setString(
                    3,
                    deliveryMethod.getDeliverySpeed()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при добавлении способа доставки"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDeliveryMethod(
            DeliveryMethod deliveryMethod
    ) {
        String sql = """
                UPDATE delivery_methods
                SET name = ?,
                    basic_price = ?,
                    delivery_speed = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setString(
                    1,
                    deliveryMethod.getName()
            );

            statement.setBigDecimal(
                    2,
                    deliveryMethod.getBasicPrice()
            );

            statement.setString(
                    3,
                    deliveryMethod.getDeliverySpeed()
            );

            statement.setInt(
                    4,
                    deliveryMethod.getId()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении способа доставки"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDeliveryMethod(int deliveryMethodId) {
        String sql = """
                DELETE FROM delivery_methods
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    deliveryMethodId
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при удалении способа доставки"
            );

            e.printStackTrace();
        }

        return false;
    }
}
package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.DealElement;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DealElementDao {

    private Connection connection;

    public DealElementDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<DealElement> getAllDealElements() {
        ArrayList<DealElement> dealElements =
                new ArrayList<DealElement>();

        String sql = """
                SELECT deal_elements.id,
                       deal_elements.document_id,
                       deal_elements.item_id,
                       deal_elements.delivery_id,
                       deal_elements.amount,
                       deal_elements.delivery_price,
                       documents.document_number,
                       items.item_name,
                       delivery_methods.name AS delivery_name
                FROM deal_elements
                INNER JOIN documents
                    ON deal_elements.document_id = documents.id
                INNER JOIN items
                    ON deal_elements.item_id = items.id
                INNER JOIN delivery_methods
                    ON deal_elements.delivery_id = delivery_methods.id
                ORDER BY deal_elements.id
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");

                int documentId =
                        result.getInt("document_id");

                int itemId =
                        result.getInt("item_id");

                int deliveryId =
                        result.getInt("delivery_id");

                int amount =
                        result.getInt("amount");

                BigDecimal deliveryPrice =
                        result.getBigDecimal("delivery_price");

                String documentNumber =
                        result.getString("document_number");

                String itemName =
                        result.getString("item_name");

                String deliveryName =
                        result.getString("delivery_name");

                DealElement dealElement =
                        new DealElement(
                                id,
                                documentId,
                                itemId,
                                deliveryId,
                                amount,
                                deliveryPrice,
                                documentNumber,
                                itemName,
                                deliveryName
                        );

                dealElements.add(dealElement);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении элементов сделок"
            );

            e.printStackTrace();
        }

        return dealElements;
    }

    public boolean addDealElement(
            DealElement dealElement
    ) {
        String sql = """
                INSERT INTO deal_elements
                (document_id,
                 item_id,
                 delivery_id,
                 amount,
                 delivery_price)
                VALUES (?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    dealElement.getDocumentId()
            );

            statement.setInt(
                    2,
                    dealElement.getItemId()
            );

            statement.setInt(
                    3,
                    dealElement.getDeliveryId()
            );

            statement.setInt(
                    4,
                    dealElement.getAmount()
            );

            statement.setBigDecimal(
                    5,
                    dealElement.getDeliveryPrice()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при добавлении элемента сделки"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDealElement(
            DealElement dealElement
    ) {
        String sql = """
                UPDATE deal_elements
                SET document_id = ?,
                    item_id = ?,
                    delivery_id = ?,
                    amount = ?,
                    delivery_price = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    dealElement.getDocumentId()
            );

            statement.setInt(
                    2,
                    dealElement.getItemId()
            );

            statement.setInt(
                    3,
                    dealElement.getDeliveryId()
            );

            statement.setInt(
                    4,
                    dealElement.getAmount()
            );

            statement.setBigDecimal(
                    5,
                    dealElement.getDeliveryPrice()
            );

            statement.setInt(
                    6,
                    dealElement.getId()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении элемента сделки"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDealElement(int dealElementId) {
        String sql = """
                DELETE FROM deal_elements
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    dealElementId
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при удалении элемента сделки"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean isDeliveryAvailable(
            int itemId,
            int deliveryId
    ) {
        String sql = """
                SELECT item_id
                FROM item_delivery
                WHERE item_id = ?
                  AND delivery_id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(1, itemId);
            statement.setInt(2, deliveryId);

            ResultSet result;
            result = statement.executeQuery();

            boolean available = result.next();

            result.close();
            statement.close();

            return available;

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при проверке способа доставки"
            );

            e.printStackTrace();
        }

        return false;
    }
}
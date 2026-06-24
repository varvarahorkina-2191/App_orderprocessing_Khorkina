package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.DealElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DealElementDao {

    private Connection connection;

    public DealElementDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public ArrayList<DealElement> getAllDealElements() {
        String sql = """
                SELECT deal_elements.id, deal_elements.document_id,
                       deal_elements.item_id, deal_elements.delivery_id,
                       deal_elements.amount, deal_elements.delivery_price,
                       documents.document_number, items.item_name,
                       delivery_methods.name AS delivery_name
                FROM deal_elements
                INNER JOIN documents ON deal_elements.document_id = documents.id
                INNER JOIN items ON deal_elements.item_id = items.id
                INNER JOIN delivery_methods ON deal_elements.delivery_id = delivery_methods.id
                ORDER BY deal_elements.id
                """;

        return executeQuery(sql, null);
    }

    public ArrayList<DealElement> searchDealElements(String searchText) {
        String sql = """
                SELECT deal_elements.id, deal_elements.document_id,
                       deal_elements.item_id, deal_elements.delivery_id,
                       deal_elements.amount, deal_elements.delivery_price,
                       documents.document_number, items.item_name,
                       delivery_methods.name AS delivery_name
                FROM deal_elements
                INNER JOIN documents ON deal_elements.document_id = documents.id
                INNER JOIN items ON deal_elements.item_id = items.id
                INNER JOIN delivery_methods ON deal_elements.delivery_id = delivery_methods.id
                WHERE documents.document_number LIKE ?
                   OR items.item_name LIKE ?
                   OR delivery_methods.name LIKE ?
                ORDER BY deal_elements.id
                """;

        return executeQuery(sql, searchText);
    }

    private ArrayList<DealElement> executeQuery(String sql, String searchText) {
        ArrayList<DealElement> dealElements = new ArrayList<DealElement>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            if (searchText != null) {
                String pattern = "%" + searchText + "%";

                statement.setString(1, pattern);
                statement.setString(2, pattern);
                statement.setString(3, pattern);
            }

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                dealElements.add(createDealElement(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении элементов сделок");
            e.printStackTrace();
        }

        return dealElements;
    }

    private DealElement createDealElement(ResultSet result) throws SQLException {
        return new DealElement(
                result.getInt("id"),
                result.getInt("document_id"),
                result.getInt("item_id"),
                result.getInt("delivery_id"),
                result.getInt("amount"),
                result.getBigDecimal("delivery_price"),
                result.getString("document_number"),
                result.getString("item_name"),
                result.getString("delivery_name")
        );
    }

    public boolean addDealElement(DealElement dealElement) {
        String sql = """
                INSERT INTO deal_elements
                (document_id, item_id, delivery_id, amount, delivery_price)
                SELECT ?, ?, ?, ?, ?
                WHERE EXISTS (
                    SELECT 1 FROM item_delivery
                    WHERE item_id = ? AND delivery_id = ?
                )
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, dealElement.getDocumentId());
            statement.setInt(2, dealElement.getItemId());
            statement.setInt(3, dealElement.getDeliveryId());
            statement.setInt(4, dealElement.getAmount());
            statement.setBigDecimal(5, dealElement.getDeliveryPrice());
            statement.setInt(6, dealElement.getItemId());
            statement.setInt(7, dealElement.getDeliveryId());

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении элемента сделки");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDealElement(DealElement dealElement) {
        String sql = """
                UPDATE deal_elements
                SET document_id = ?, item_id = ?, delivery_id = ?,
                    amount = ?, delivery_price = ?
                WHERE id = ?
                  AND EXISTS (
                      SELECT 1 FROM item_delivery
                      WHERE item_id = ? AND delivery_id = ?
                  )
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, dealElement.getDocumentId());
            statement.setInt(2, dealElement.getItemId());
            statement.setInt(3, dealElement.getDeliveryId());
            statement.setInt(4, dealElement.getAmount());
            statement.setBigDecimal(5, dealElement.getDeliveryPrice());
            statement.setInt(6, dealElement.getId());
            statement.setInt(7, dealElement.getItemId());
            statement.setInt(8, dealElement.getDeliveryId());

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении элемента сделки");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDealElement(int dealElementId) {
        String sql = "DELETE FROM deal_elements WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, dealElementId);

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении элемента сделки");
            e.printStackTrace();
        }

        return false;
    }

    public boolean isDeliveryAvailable(int itemId, int deliveryId) {
        String sql = """
                SELECT 1 FROM item_delivery
                WHERE item_id = ? AND delivery_id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, itemId);
            statement.setInt(2, deliveryId);

            ResultSet result = statement.executeQuery();
            boolean available = result.next();

            result.close();
            statement.close();

            return available;
        } catch (SQLException e) {
            System.out.println("Ошибка при проверке способа доставки");
            e.printStackTrace();
        }

        return false;
    }
}
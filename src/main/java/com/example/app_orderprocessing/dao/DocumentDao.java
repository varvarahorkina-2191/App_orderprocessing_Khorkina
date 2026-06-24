package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.Document;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocumentDao {

    private Connection connection;

    public DocumentDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public ArrayList<Document> getAllDocuments() {
        String sql = """
                SELECT documents.id, documents.customer_id,
                       customers.customer_name, documents.document_number,
                       documents.purchase_date,
                       COALESCE(SUM(items.price * deal_elements.amount
                       + deal_elements.delivery_price), 0) AS total_amount
                FROM documents
                INNER JOIN customers ON documents.customer_id = customers.id
                LEFT JOIN deal_elements ON documents.id = deal_elements.document_id
                LEFT JOIN items ON deal_elements.item_id = items.id
                GROUP BY documents.id, documents.customer_id,
                         customers.customer_name, documents.document_number,
                         documents.purchase_date
                ORDER BY documents.id
                """;

        return executeQuery(sql, null);
    }

    public ArrayList<Document> searchDocuments(String searchText) {
        String sql = """
                SELECT documents.id, documents.customer_id,
                       customers.customer_name, documents.document_number,
                       documents.purchase_date,
                       COALESCE(SUM(items.price * deal_elements.amount
                       + deal_elements.delivery_price), 0) AS total_amount
                FROM documents
                INNER JOIN customers ON documents.customer_id = customers.id
                LEFT JOIN deal_elements ON documents.id = deal_elements.document_id
                LEFT JOIN items ON deal_elements.item_id = items.id
                WHERE documents.document_number LIKE ?
                   OR customers.customer_name LIKE ?
                   OR CAST(documents.purchase_date AS CHAR) LIKE ?
                GROUP BY documents.id, documents.customer_id,
                         customers.customer_name, documents.document_number,
                         documents.purchase_date
                ORDER BY documents.id
                """;

        return executeQuery(sql, searchText);
    }

    private ArrayList<Document> executeQuery(String sql, String searchText) {
        ArrayList<Document> documents = new ArrayList<Document>();

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
                documents.add(createDocument(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении документов");
            e.printStackTrace();
        }

        return documents;
    }

    private Document createDocument(ResultSet result) throws SQLException {
        BigDecimal totalAmount = result.getBigDecimal("total_amount");

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        return new Document(
                result.getInt("id"),
                result.getInt("customer_id"),
                result.getString("customer_name"),
                result.getString("document_number"),
                result.getDate("purchase_date").toLocalDate(),
                totalAmount
        );
    }

    public boolean addDocument(Document document) {
        String sql = """
                INSERT INTO documents (customer_id, document_number, purchase_date)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, document.getCustomerId());
            statement.setString(2, document.getDocumentNumber());
            statement.setDate(3, Date.valueOf(document.getPurchaseDate()));

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении документа");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDocument(Document document) {
        String sql = """
                UPDATE documents
                SET customer_id = ?, document_number = ?, purchase_date = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, document.getCustomerId());
            statement.setString(2, document.getDocumentNumber());
            statement.setDate(3, Date.valueOf(document.getPurchaseDate()));
            statement.setInt(4, document.getId());

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении документа");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDocument(int documentId) {
        String sql = "DELETE FROM documents WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, documentId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении документа");
            e.printStackTrace();
        }

        return false;
    }
}
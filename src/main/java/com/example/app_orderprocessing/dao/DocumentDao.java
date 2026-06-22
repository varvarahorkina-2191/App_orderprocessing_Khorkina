package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.Document;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocumentDao {

    private Connection connection;

    public DocumentDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> documents =
                new ArrayList<Document>();

        String sql = """
                SELECT documents.id,
                       documents.customer_id,
                       customers.customer_name,
                       documents.document_number,
                       documents.purchase_date
                FROM documents
                INNER JOIN customers
                    ON documents.customer_id = customers.id
                ORDER BY documents.id
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");

                int customerId =
                        result.getInt("customer_id");

                String customerName =
                        result.getString("customer_name");

                String documentNumber =
                        result.getString("document_number");

                Date sqlDate =
                        result.getDate("purchase_date");

                Document document = new Document(
                        id,
                        customerId,
                        customerName,
                        documentNumber,
                        sqlDate.toLocalDate()
                );

                documents.add(document);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении документов"
            );

            e.printStackTrace();
        }

        return documents;
    }

    public boolean addDocument(Document document) {
        String sql = """
                INSERT INTO documents
                (customer_id, document_number, purchase_date)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    document.getCustomerId()
            );

            statement.setString(
                    2,
                    document.getDocumentNumber()
            );

            statement.setDate(
                    3,
                    Date.valueOf(document.getPurchaseDate())
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при добавлении документа"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean updateDocument(Document document) {
        String sql = """
                UPDATE documents
                SET customer_id = ?,
                    document_number = ?,
                    purchase_date = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    document.getCustomerId()
            );

            statement.setString(
                    2,
                    document.getDocumentNumber()
            );

            statement.setDate(
                    3,
                    Date.valueOf(document.getPurchaseDate())
            );

            statement.setInt(
                    4,
                    document.getId()
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении документа"
            );

            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteDocument(int documentId) {
        String sql = """
                DELETE FROM documents
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    documentId
            );

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при удалении документа"
            );

            e.printStackTrace();
        }

        return false;
    }
}
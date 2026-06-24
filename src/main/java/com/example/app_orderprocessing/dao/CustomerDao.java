package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    private Connection connection;

    public CustomerDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();

        String sql = """
                SELECT id, customer_name, address, phone_number, contact_person
                FROM customers
                ORDER BY id
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                customers.add(createCustomer(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении заказчиков");
            e.printStackTrace();
        }

        return customers;
    }

    public List<Customer> searchCustomers(String searchText) {
        List<Customer> customers = new ArrayList<Customer>();

        String sql = """
                SELECT id, customer_name, address, phone_number, contact_person
                FROM customers
                WHERE customer_name LIKE ?
                   OR address LIKE ?
                   OR phone_number LIKE ?
                   OR contact_person LIKE ?
                ORDER BY id
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String pattern = "%" + searchText + "%";

            statement.setString(1, pattern);
            statement.setString(2, pattern);
            statement.setString(3, pattern);
            statement.setString(4, pattern);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                customers.add(createCustomer(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске заказчиков");
            e.printStackTrace();
        }

        return customers;
    }

    public Customer getCustomerById(int id) {
        String sql = """
                SELECT id, customer_name, address, phone_number, contact_person
                FROM customers
                WHERE id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            Customer customer = null;

            if (result.next()) {
                customer = createCustomer(result);
            }

            result.close();
            statement.close();

            return customer;
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске заказчика");
            e.printStackTrace();
        }

        return null;
    }

    public boolean addCustomer(Customer customer) {
        String sql = """
                INSERT INTO customers
                (customer_name, address, phone_number, contact_person)
                VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getContactPerson());

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении заказчика");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = """
                UPDATE customers
                SET customer_name = ?, address = ?, phone_number = ?, contact_person = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getContactPerson());
            statement.setInt(5, customer.getId());

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении заказчика");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rows = statement.executeUpdate();
            statement.close();

            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении заказчика");
            e.printStackTrace();
        }

        return false;
    }

    private Customer createCustomer(ResultSet result) throws SQLException {
        return new Customer(
                result.getInt("id"),
                result.getString("customer_name"),
                result.getString("address"),
                result.getString("phone_number"),
                result.getString("contact_person")
        );
    }
}
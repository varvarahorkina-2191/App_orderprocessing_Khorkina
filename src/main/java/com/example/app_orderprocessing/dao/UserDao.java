package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public User findByLogin(String login) {
        String sql = """
                SELECT *
                FROM users
                WHERE login = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = new User(
                        result.getInt("id"),
                        result.getInt("active_role_id"),
                        result.getString("login"),
                        result.getString("hash_password")
                );

                result.close();
                statement.close();

                return user;
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователя");
            e.printStackTrace();
        }

        return null;
    }

    public boolean addUser(User user) {
        String sql = """
                INSERT INTO users
                (active_role_id, login, hash_password)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, user.getActiveRoleId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getHashPassword());

            int rows = statement.executeUpdate();

            statement.close();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя");
            e.printStackTrace();

            return false;
        }
    }
}
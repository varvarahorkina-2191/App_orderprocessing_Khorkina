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
                int id = result.getInt("id");
                int roleId = result.getInt("active_role_id");
                String userLogin = result.getString("login");
                String passwordHash = result.getString("hash_password");

                User user = new User(
                        id,
                        roleId,
                        userLogin,
                        passwordHash
                );

                result.close();
                statement.close();

                return user;
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Ошибка поиска пользователя");
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

            int result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Ошибка добавления пользователя");
            e.printStackTrace();
        }

        return false;
    }

    public boolean addUserRole(int userId, int roleId) {
        String sql = """
                INSERT INTO user_roles
                (user_id, role_id)
                VALUES (?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, roleId);

            int result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Ошибка добавления роли");
            e.printStackTrace();
        }

        return false;
    }
}
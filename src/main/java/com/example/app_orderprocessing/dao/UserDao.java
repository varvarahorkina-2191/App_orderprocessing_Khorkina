package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    private Connection connection;

    public UserDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public User findByLogin(String login) {
        String sql = """
                SELECT id, login, hash_password, active_role_id
                FROM users
                WHERE login = ?
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            ResultSet result = statement.executeQuery();
            User user = null;

            if (result.next()) {
                user = new User(
                        result.getInt("id"),
                        result.getInt("active_role_id"),
                        result.getString("login"),
                        result.getString("hash_password")
                );
            }

            result.close();
            statement.close();

            return user;
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователя");
            e.printStackTrace();
        }

        return null;
    }

    public boolean registerCustomer(String login, String hashPassword) {
        String roleSql = "SELECT id FROM roles WHERE role_name = 'CUSTOMER'";

        String userSql = """
                INSERT INTO users (login, hash_password, active_role_id)
                VALUES (?, ?, ?)
                """;

        String userRoleSql = """
                INSERT INTO user_roles (role_id, user_id)
                VALUES (?, ?)
                """;

        PreparedStatement roleStatement = null;
        PreparedStatement userStatement = null;
        PreparedStatement userRoleStatement = null;
        ResultSet roleResult = null;
        ResultSet keys = null;

        try {
            connection.setAutoCommit(false);

            roleStatement = connection.prepareStatement(roleSql);
            roleResult = roleStatement.executeQuery();

            if (roleResult.next() == false) {
                connection.rollback();
                return false;
            }

            int roleId = roleResult.getInt("id");

            userStatement = connection.prepareStatement(
                    userSql,
                    Statement.RETURN_GENERATED_KEYS
            );

            userStatement.setString(1, login);
            userStatement.setString(2, hashPassword);
            userStatement.setInt(3, roleId);

            if (userStatement.executeUpdate() == 0) {
                connection.rollback();
                return false;
            }

            keys = userStatement.getGeneratedKeys();

            if (keys.next() == false) {
                connection.rollback();
                return false;
            }

            int userId = keys.getInt(1);

            userRoleStatement = connection.prepareStatement(userRoleSql);
            userRoleStatement.setInt(1, roleId);
            userRoleStatement.setInt(2, userId);

            if (userRoleStatement.executeUpdate() == 0) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }

            System.out.println("Ошибка при регистрации пользователя");
            e.printStackTrace();
            return false;
        } finally {
            closeResources(keys, roleResult, userRoleStatement, userStatement, roleStatement);

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateLogin(int userId, String newLogin) {
        String sql = "UPDATE users SET login = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newLogin);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении логина");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET hash_password = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, newPasswordHash);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении пароля");
            e.printStackTrace();
        }

        return false;
    }

    private void closeResources(
            ResultSet keys,
            ResultSet roleResult,
            PreparedStatement userRoleStatement,
            PreparedStatement userStatement,
            PreparedStatement roleStatement
    ) {
        try {
            if (keys != null) {
                keys.close();
            }

            if (roleResult != null) {
                roleResult.close();
            }

            if (userRoleStatement != null) {
                userRoleStatement.close();
            }

            if (userStatement != null) {
                userStatement.close();
            }

            if (roleStatement != null) {
                roleStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
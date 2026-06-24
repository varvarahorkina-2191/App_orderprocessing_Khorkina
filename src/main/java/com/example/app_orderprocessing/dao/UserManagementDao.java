package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.UserAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManagementDao {

    private Connection connection;

    public UserManagementDao() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public ArrayList<UserAccess> getAllUsers() {
        ArrayList<UserAccess> users = new ArrayList<UserAccess>();

        String sql = """
                SELECT users.id, users.login, users.active_role_id, roles.role_name
                FROM users
                JOIN roles ON users.active_role_id = roles.id
                ORDER BY users.id
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UserAccess user = new UserAccess(
                        result.getInt("id"),
                        result.getString("login"),
                        result.getInt("active_role_id"),
                        result.getString("role_name")
                );

                users.add(user);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении пользователей");
            e.printStackTrace();
        }

        return users;
    }

    public boolean addRoleToUser(int userId, int roleId) {
        String sql = """
                INSERT INTO user_roles (role_id, user_id)
                VALUES (?, ?)
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, roleId);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении роли пользователю");
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeRoleFromUser(int userId, int roleId, int activeRoleId) {
        String countSql = "SELECT COUNT(*) AS role_count FROM user_roles WHERE user_id = ?";

        String deleteSql = """
                DELETE FROM user_roles
                WHERE user_id = ? AND role_id = ?
                """;

        String findRoleSql = """
                SELECT role_id
                FROM user_roles
                WHERE user_id = ?
                ORDER BY role_id
                LIMIT 1
                """;

        String updateRoleSql = "UPDATE users SET active_role_id = ? WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            PreparedStatement countStatement = connection.prepareStatement(countSql);
            countStatement.setInt(1, userId);

            ResultSet countResult = countStatement.executeQuery();

            if (countResult.next() == false || countResult.getInt("role_count") <= 1) {
                countResult.close();
                countStatement.close();
                connection.rollback();
                return false;
            }

            countResult.close();
            countStatement.close();

            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, userId);
            deleteStatement.setInt(2, roleId);

            int deleted = deleteStatement.executeUpdate();
            deleteStatement.close();

            if (deleted == 0) {
                connection.rollback();
                return false;
            }

            if (roleId == activeRoleId) {
                PreparedStatement findStatement = connection.prepareStatement(findRoleSql);
                findStatement.setInt(1, userId);

                ResultSet roleResult = findStatement.executeQuery();

                if (roleResult.next() == false) {
                    roleResult.close();
                    findStatement.close();
                    connection.rollback();
                    return false;
                }

                int newRoleId = roleResult.getInt("role_id");

                roleResult.close();
                findStatement.close();

                PreparedStatement updateStatement = connection.prepareStatement(updateRoleSql);
                updateStatement.setInt(1, newRoleId);
                updateStatement.setInt(2, userId);

                int updated = updateStatement.executeUpdate();
                updateStatement.close();

                if (updated == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }

            System.out.println("Ошибка при удалении роли пользователя");
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя");
            e.printStackTrace();
        }

        return false;
    }
}
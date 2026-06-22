package com.example.app_orderprocessing.dao;

import com.example.app_orderprocessing.database.DatabaseConnection;
import com.example.app_orderprocessing.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDao {

    private Connection connection;

    public RoleDao() {
        connection = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public ArrayList<Role> getUserRoles(int userId) {
        ArrayList<Role> roles = new ArrayList<Role>();

        String sql = """
                SELECT roles.id, roles.role_name
                FROM roles
                INNER JOIN user_roles
                ON roles.id = user_roles.role_id
                WHERE user_roles.user_id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet result;
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String roleName = result.getString("role_name");

                Role role = new Role(
                        id,
                        roleName
                );

                roles.add(role);
            }

            result.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при получении ролей пользователя"
            );

            e.printStackTrace();
        }

        return roles;
    }

    public boolean updateActiveRole(int userId, int roleId) {
        String sql = """
                UPDATE users
                SET active_role_id = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement statement;
            statement = connection.prepareStatement(sql);

            statement.setInt(1, roleId);
            statement.setInt(2, userId);

            int result;
            result = statement.executeUpdate();

            statement.close();

            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(
                    "Ошибка при изменении активной роли"
            );

            e.printStackTrace();
        }

        return false;
    }
}
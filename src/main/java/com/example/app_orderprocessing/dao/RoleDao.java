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
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public Role findByName(String roleName) {
        String sql = "SELECT id, role_name FROM roles WHERE role_name = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, roleName);

            ResultSet result = statement.executeQuery();
            Role role = null;

            if (result.next()) {
                role = createRole(result);
            }

            result.close();
            statement.close();

            return role;
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске роли");
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<Role>();

        String sql = """
                SELECT id, role_name
                FROM roles
                ORDER BY id
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                roles.add(createRole(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении ролей");
            e.printStackTrace();
        }

        return roles;
    }

    public ArrayList<Role> getUserRoles(int userId) {
        ArrayList<Role> roles = new ArrayList<Role>();

        String sql = """
                SELECT roles.id, roles.role_name
                FROM roles
                JOIN user_roles ON roles.id = user_roles.role_id
                WHERE user_roles.user_id = ?
                ORDER BY roles.id
                """;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                roles.add(createRole(result));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при получении ролей пользователя");
            e.printStackTrace();
        }

        return roles;
    }

    public boolean updateActiveRole(int userId, int roleId) {
        String sql = "UPDATE users SET active_role_id = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, roleId);
            statement.setInt(2, userId);

            int result = statement.executeUpdate();
            statement.close();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при изменении активной роли");
            e.printStackTrace();
        }

        return false;
    }

    private Role createRole(ResultSet result) throws SQLException {
        return new Role(
                result.getInt("id"),
                result.getString("role_name")
        );
    }
}
package com.example.app_orderprocessing.model;

public class UserRole {

    private int roleId;
    private int userId;

    public UserRole() {
    }

    public UserRole(int roleId, int userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
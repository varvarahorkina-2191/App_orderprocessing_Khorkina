package com.example.app_orderprocessing.model;

public class UserAccess {

    private int id;
    private String login;
    private int activeRoleId;
    private String activeRoleName;

    public UserAccess(int id, String login, int activeRoleId, String activeRoleName) {
        this.id = id;
        this.login = login;
        this.activeRoleId = activeRoleId;
        this.activeRoleName = activeRoleName;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getActiveRoleId() {
        return activeRoleId;
    }

    public String getActiveRoleName() {
        return activeRoleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setActiveRoleId(int activeRoleId) {
        this.activeRoleId = activeRoleId;
    }

    public void setActiveRoleName(String activeRoleName) {
        this.activeRoleName = activeRoleName;
    }
}
package com.example.app_orderprocessing.model;

public class User {

    private int id;
    private int activeRoleId;
    private String login;
    private String hashPassword;

    public User() {
    }

    public User(int activeRoleId, String login, String hashPassword) {
        this.activeRoleId = activeRoleId;
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public User(int id, int activeRoleId, String login, String hashPassword) {
        this.id = id;
        this.activeRoleId = activeRoleId;
        this.login = login;
        this.hashPassword = hashPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActiveRoleId() {
        return activeRoleId;
    }

    public void setActiveRoleId(int activeRoleId) {
        this.activeRoleId = activeRoleId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
}
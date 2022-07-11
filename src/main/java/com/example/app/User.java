package com.example.app;

// Объект "Пользователь"
public class User {
    private int id;
    private String login;
    private String password;
    private String group;
    private String level;

    public User(String login, String password, String group){
        this.login = login;
        this.password = password;
        this.group = group;
        this.level = null;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

package com.example.app;

public class User {
    private String last_name;
    private String first_name;
    private String second_name;
    private String login;
    private String password;

    public User(String last_name, String first_name, String second_name,
                String login, String password){
        this.last_name = last_name;
        this.first_name = first_name;
        this.second_name = second_name;
        this.login = login;
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name){
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name){
        this.second_name = second_name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}

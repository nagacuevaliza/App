package com.example.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Handler{
    Connection connection;

    public Connection getConnection()
            throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2004_app",
                "std_2004_app", "std_2004_app");

        return connection;
    }

    public void signUpUser(User user){
        String insert = "INSERT INTO " + Constants.USER_TABLE + "(" + Constants.USERS_LASTNAME + "," +
                Constants.USERS_FIRSTNAME + "," + Constants.USERS_SECONDNAME + "," +
                Constants.USERS_LOGIN + "," + Constants.USERS_PASSWORD + ")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(insert);
            prSt.setString(1, user.getLast_name());
            prSt.setString(2, user.getFirst_name());
            prSt.setString(3, user.getSecond_name());
            prSt.setString(4, user.getLogin());
            prSt.setString(5, user.getPassword());

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(User user){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Constants.USER_TABLE + " WHERE " +
                Constants.USERS_LOGIN + "=? AND " + Constants.USERS_PASSWORD + "=?";
        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());

            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
}

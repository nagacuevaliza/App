package com.example.app;

import java.sql.*;

public class Handler{
    static Connection connection;

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2004_app",
                "std_2004_app", "std_2004_app");

        return connection;
    }

    public void signUpUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constants.USER_TABLE + " (" + Constants.USERS_LOGIN + ", " +
                Constants.USERS_PASSWORD + ", " + Constants.USERS_GROUP + ", " + Constants.USERS_LEVEL + ") " +
                "VALUES (?, ?, ?, ?)";

            PreparedStatement prSt = getConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPassword());
            prSt.setString(3, user.getGroup());
            prSt.setString(4, "user");

            prSt.executeUpdate();
            System.out.println(prSt);
//            ResultSet getID = prSt.getGeneratedKeys();
//            getID.next();
//            int id = getID.getInt(1);
    }

    public ResultSet getUsersData(User user){
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

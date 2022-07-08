package com.example.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Handler{
    Connection connection;

    public Connection getConnection()
            throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2004_app",
                "std_2004_app", "std_2004_app");

        return connection;
    }

}

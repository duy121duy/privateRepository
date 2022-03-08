package com.example.jdbcwebservice.utils;


import com.example.jdbcwebservice.config.DbConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DbConfiguration.CONNECTION_URL, DbConfiguration.USER_NAME, DbConfiguration.PASSWORD);
        if (connection != null) {
            System.out.println("Connect Successful");
            return connection;
        } else {
            System.out.println("Connect Failed");
        }
        return null;
    }
}

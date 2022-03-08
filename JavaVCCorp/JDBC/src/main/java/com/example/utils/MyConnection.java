package com.example.utils;

import com.example.configuration.DbConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection CONNECTION = null;

    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        CONNECTION = DriverManager.getConnection(DbConfiguration.CONNECTION_URL, DbConfiguration.USER_NAME, DbConfiguration.PASSWORD);
        if (CONNECTION != null) {
            System.out.println("Connect Successful");
            return CONNECTION;
        } else {
            System.out.println("Connect Failed");
        }
        return null;
    }

    public static void close() {
        if (CONNECTION != null) {
            try {
                System.out.println("Connection closed");
                CONNECTION.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

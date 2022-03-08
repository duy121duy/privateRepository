package com.example;

import com.example.utils.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionExample {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = MyConnection.connect();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);

        try {
            statement.execute("LOCK TABLE messages WRITE");
            statement.executeUpdate("UPDATE messages SET message = 'new message 3' WHERE id = 3");
            Thread.sleep(10000);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }

        if (statement != null) {
            System.out.println("Close statement");
            statement.close();
        }

        MyConnection.close();
    }
}

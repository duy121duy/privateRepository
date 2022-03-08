package com.example.Bai6;

import com.example.utils.MyConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Bai6 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = MyConnection.connect();
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            statement.executeUpdate("UPDATE titles SET to_date = CURDATE() WHERE emp_no = 10071 AND to_date = '9999-01-01'");
            statement.executeUpdate("INSERT INTO titles VALUES(10071, 'Senior Staff', CURDATE(), '9999-01-01')");

            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MyConnection.close();
        }
    }
}

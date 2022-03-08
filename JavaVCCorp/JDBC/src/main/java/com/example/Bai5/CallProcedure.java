package com.example.Bai5;

import com.example.MySQLConnection;
import com.example.entities.ResultBai5;
import com.example.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CallProcedure {
    public static ResultBai5 changeDept(int id, String newTitle, String newDept) {
        Connection connection = MyConnection.CONNECTION;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("CALL changeDept(?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, newTitle);
            preparedStatement.setString(3, newDept);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new ResultBai5(resultSet.getInt("emp_no"), resultSet.getString("fullname"), resultSet.getString("gender"),
                    resultSet.getString("title"), resultSet.getString("dept_no"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQLConnection.closeResultSet(preparedStatement, resultSet);
        }
        return null;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = MyConnection.connect();
            ResultBai5 resultBai5 = changeDept(10012, "Senior Staff", "d010");
            System.out.println(resultBai5);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            MyConnection.close();
        }
    }
}

package com.example;

import com.example.entities.Message;
import com.example.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnection {
    private static final String connectionURL = "jdbc:mysql://localhost:3306/bsql_shoppingsystem";
    private static final String userName = "root";
    private static final String password = "VTD.240899@";

    public static void closeResultSet(PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Message> findAll() throws SQLException, ClassNotFoundException {
        List<Message> messages = new ArrayList<>();
        PreparedStatement preparedStatement = MyConnection.CONNECTION.prepareStatement("SELECT * FROM bsql_shoppingsystem.messages", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.first();
        do {
            Message message = new Message();
            message.setId(resultSet.getInt("id"));
            message.setMessage(resultSet.getString("message"));
            messages.add(message);
        } while (resultSet.next());
        closeResultSet(preparedStatement, resultSet);
        return messages;
    }

    public static Message addMessage(Message message) throws SQLException, ClassNotFoundException {
        Connection connection = MyConnection.CONNECTION;
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO messages VALUES(null, ?)");
        preparedStatement.setString(1, message.getMessage());
        int rs = preparedStatement.executeUpdate();
        if (rs > 0) {
            PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM bsql_shoppingsystem.messages",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = ps2.executeQuery();
            resultSet.last();
            Message result = new Message(resultSet.getInt("id"), resultSet.getString("message"));
            closeResultSet(ps2, resultSet);
            return result;
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = MyConnection.connect();

        connection.setAutoCommit(false);

        Statement statement = connection.createStatement();

        for (int i = 0; i < 50; i++) {
            Message message = new Message(0, "Hello " + i);
            String sql = "INSERT INTO messages(message) VALUES('" + message.getMessage() + "')";
            statement.addBatch(sql);
            if (i % 10 == 0) {
                statement.executeBatch();
            }
        }

        statement.executeBatch();

        connection.commit();

        MyConnection.close();
    }
}

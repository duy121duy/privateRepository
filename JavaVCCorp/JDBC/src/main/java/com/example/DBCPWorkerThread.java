package com.example;

import com.example.utils.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

public class DBCPWorkerThread extends Thread {
    private CountDownLatch latch;
    private String taskName;

    public DBCPWorkerThread(CountDownLatch latch, String taskName) {
        this.latch = latch;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Starting. Task = " + taskName);
        execute();
        latch.countDown();
        System.out.println(Thread.currentThread().getName() + " Finished.");
    }

    private void execute() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM messages";
            Connection connection = DBCPDataSource.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Thread.sleep(2000);
            rs.next();
            System.out.println("Task = " + taskName + ": Run SQL successfully " + rs.getInt("total"));
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


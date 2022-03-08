package com.example.utils;

import com.example.configuration.DbConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource BASICDATASOURCE = new BasicDataSource();

    static {
        BASICDATASOURCE.setDriverClassName(DbConfiguration.DB_DRIVER);
        BASICDATASOURCE.setUrl(DbConfiguration.CONNECTION_URL);
        BASICDATASOURCE.setUsername(DbConfiguration.USER_NAME);
        BASICDATASOURCE.setPassword(DbConfiguration.PASSWORD);
        BASICDATASOURCE.setMinIdle(DbConfiguration.DB_MIN_CONNECTIONS); // minimum number of idle connections in the pool
        BASICDATASOURCE.setInitialSize(DbConfiguration.DB_MIN_CONNECTIONS);
        BASICDATASOURCE.setMaxIdle(DbConfiguration.DB_MAX_CONNECTIONS); // maximum number of idle connections in the pool
        BASICDATASOURCE.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return BASICDATASOURCE.getConnection();
    }
}

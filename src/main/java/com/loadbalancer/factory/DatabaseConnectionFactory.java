package com.loadbalancer.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.loadbalancer.util.DatabaseType;

public class DatabaseConnectionFactory {
    public static Connection createConnection(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        String url;
        switch (type) {
            case MYSQL:
                url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
                return DriverManager.getConnection(url, user, password);
            case POSTGRESQL:
                url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
                return DriverManager.getConnection(url, user, password);
            default:
                throw new IllegalArgumentException("Unknown database type");
        }
    }
}
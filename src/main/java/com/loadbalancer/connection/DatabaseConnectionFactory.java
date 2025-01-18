package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    public static DatabaseConnectionWrapper createConnection(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        String url;
        Connection conn;
        switch (type) {
            case MYSQL:
                url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
                conn = DriverManager.getConnection(url, user, password);
                return new DatabaseConnectionWrapper(conn);
            case POSTGRESQL:
                url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
                conn = DriverManager.getConnection(url, user, password);
                return new DatabaseConnectionWrapper(conn);
            default:
                throw new IllegalArgumentException("Unknown database type");
        }
    }
}
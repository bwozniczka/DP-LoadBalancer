package com.loadbalancer.connection;

import com.loadbalancer.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Fix Error that occure when there is nothing on that specific port 
public class DatabaseConnectionFactory {
    public static DatabaseConnectionWrapper createConnection(DatabaseType type, String host, int port, String dbName, String user, String password) {
        String url;
        Connection conn = null;
        try {
            switch (type) {
                case MYSQL:
                    url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
                    conn = DriverManager.getConnection(url, user, password);
                    break;
                case POSTGRESQL:
                    url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
                    conn = DriverManager.getConnection(url, user, password);
                    break;
                case SQLSERVER:
                    url = String.format("jdbc:sqlserver://%s:%d;databaseName=%s", host, port, dbName);
                    conn = DriverManager.getConnection(url, user, password);
                    break;
                case ORACLE:
                    url = String.format("jdbc:oracle:thin:@%s:%d:%s", host, port, dbName);
                    conn = DriverManager.getConnection(url, user, password);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown database type");
            }
            return new DatabaseConnectionWrapper(conn, url, user, password);
        } catch (SQLException e) {
            Log.error("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}
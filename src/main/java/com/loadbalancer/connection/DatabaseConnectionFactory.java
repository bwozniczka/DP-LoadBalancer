package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;

import com.loadbalancer.logger.LoggerPanelFactory;

public class DatabaseConnectionFactory {
    private static final Logger logger = LoggerPanelFactory.getLogger(DatabaseConnectionFactory.class);

    public static DatabaseConnectionWrapper createConnection(DatabaseType type, String host, int port, String dbName, String user, String password) {
        String url;
        Connection conn = null;
        switch (type) {
                case MYSQL:
                    url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
                    break;
                case POSTGRESQL:
                    url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
                    break;
                case SQLSERVER:
                    url = String.format("jdbc:sqlserver://%s:%d;databaseName=%s", host, port, dbName);
                    break;
                case ORACLE:
                    url = String.format("jdbc:oracle:thin:@%s:%d:%s", host, port, dbName);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown database type");
            }

        try {
            conn = DriverManager.getConnection(url, user, password);
            return new DatabaseConnectionWrapper(conn, String.format("%s:%s:%s", type, host, port), url, user, password);

        } catch (SQLException e) {
            logger.error("Failed to connect to database (will try in the future): " + e.getMessage());
            return new DatabaseConnectionWrapper(null, String.format("%s:%s:%s", type, host, port), url, user, password);
        }
    }
}
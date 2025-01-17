package com.loadbalancer.factory;

import com.loadbalancer.connection.DatabaseConnection;
import com.loadbalancer.connection.MySQLConnection;
import com.loadbalancer.connection.PostgreSQLConnection;

public class DatabaseConnectionFactory {
    public static DatabaseConnection createConnection(String type, String host, int port, String dbName, String user, String password) {
        switch (type) {
            case "MySQL":
                return new MySQLConnection(host, port, dbName, user, password);
            case "PostgreSQL":
                return new PostgreSQLConnection(host, port, dbName, user, password);
            default:
                throw new IllegalArgumentException("Unknown database type");
        }
    }
}
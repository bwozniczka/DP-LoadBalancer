package com.loadbalancer.facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//****************************
// Design Pattern: Facade
//****************************

// TODO: don't return objects, rather implement function to query...
// TODO: Find a way to "index" connection (dictionary?)
public class DatabaseConnectionManagerFacade {
    private Connection postgresConnection1;
    private Connection postgresConnection2;
    private Connection mysqlConnection;

    public void connect() throws SQLException {
        postgresConnection1 = createPostgresConnection("localhost", 5432, "db1", "user1", "password1");
        postgresConnection2 = createPostgresConnection("localhost", 5433, "db2", "user2", "password2");
        mysqlConnection = createMysqlConnection("localhost", 3306, "db3", "user3", "password3");
    }

    private Connection createPostgresConnection(String host, int port, String dbName, String user, String password) throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
        return DriverManager.getConnection(url, user, password);
    }

    private Connection createMysqlConnection(String host, int port, String dbName, String user, String password) throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
        return DriverManager.getConnection(url, user, password);
    }

    public void close() {
        try {
            if (postgresConnection1 != null && !postgresConnection1.isClosed()) postgresConnection1.close();
            if (postgresConnection2 != null && !postgresConnection2.isClosed()) postgresConnection2.close();
            if (mysqlConnection != null && !mysqlConnection.isClosed()) mysqlConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getPostgresConnection1() {
        return postgresConnection1;
    }

    public Connection getPostgresConnection2() {
        return postgresConnection2;
    }

    public Connection getMysqlConnection() {
        return mysqlConnection;
    }
}

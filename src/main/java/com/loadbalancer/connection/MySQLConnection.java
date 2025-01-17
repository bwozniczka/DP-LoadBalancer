package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements DatabaseConnection {
    private Connection connection;
    private String url;
    private String user;
    private String password;

    public MySQLConnection(String host, int port, String dbName, String user, String password) {
        this.url = String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
        this.user = user;
        this.password = password;
    }

    @Override
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeQuery(String query) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.createStatement().execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAvailable() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
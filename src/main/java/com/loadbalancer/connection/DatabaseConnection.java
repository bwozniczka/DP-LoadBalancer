package com.loadbalancer.connection;

public interface DatabaseConnection {
    void connect();
    void disconnect();
    void executeQuery(String query);
    boolean isAvailable();
}
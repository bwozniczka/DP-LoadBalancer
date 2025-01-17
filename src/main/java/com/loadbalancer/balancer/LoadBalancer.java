package com.loadbalancer.balancer;

import com.loadbalancer.connection.DatabaseConnection;
import java.util.List;

public class LoadBalancer {
    private static LoadBalancer instance;
    private List<DatabaseConnection> databases;
    private LoadBalancingStrategy strategy;

    // Private constructor to prevent instantiation
    private LoadBalancer() {}

    // Public method to provide access to the single instance
    public static synchronized LoadBalancer getInstance() {
        if (instance == null) {
            instance = new LoadBalancer();
        }
        return instance;
    }

    // Method to set the load balancing strategy
    public void setStrategy(LoadBalancingStrategy strategy) {
        this.strategy = strategy;
    }

    // Method to set the list of databases
    public void setDatabases(List<DatabaseConnection> databases) {
        this.databases = databases;
    }

    // Method to synchronize queries across all databases
    // public void synchronizeQuery(String query) {
    //     for (DatabaseConnection db : databases) {
    //         db.executeQuery(query);
    //     }
    // }

    // Method to get a database for SELECT operations based on the strategy
    public DatabaseConnection getDatabaseToQuery() {
        return strategy.chooseDatabase(databases);
    }
}
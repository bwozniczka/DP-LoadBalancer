package com.loadbalancer.balancer;

import com.loadbalancer.facade.DatabaseConnectionManagerFacade;

import java.sql.Connection;

//****************************
// Design Pattern: Singleton (Bill Pugh implementation)
//****************************

public class LoadBalancer {
    private DatabaseConnectionManagerFacade databaseManager;
    private LoadBalancingStrategy strategy;

    private LoadBalancer() {}

    private static class LoadBalancerHelper {
        private static final LoadBalancer INSTANCE = new LoadBalancer();
    }

    public static LoadBalancer getInstance() {
        return LoadBalancerHelper.INSTANCE;
    }

    public void initialize(LoadBalancingStrategy strategy, DatabaseConnectionManagerFacade databaseManager) {
        this.strategy = strategy;
        this.databaseManager = databaseManager;
    }

    // Method to synchronize queries across all databases
    // public void synchronizeQuery(String query) {
    //     for (DatabaseConnection db : databases) {
    //         db.executeQuery(query);
    //     }
    // }

    // Method to get a database for SELECT operations based on the strategy
    public Connection getDatabaseToQuery() {
        return strategy.chooseDatabase(databaseManager.getConnections());
    }
}
package com.loadbalancer.balancer;

import java.util.List;

import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.connection.DatabaseConnectionWrapper;

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
    public List<DatabaseConnectionWrapper> getDatabaseConnections() {
        return databaseManager.getConnections();
    }

    // Method to get a database for SELECT operations based on the strategy
    public DatabaseConnectionWrapper getDatabaseConnection() {
        return strategy.chooseDatabase(databaseManager.getConnections());
    }
}
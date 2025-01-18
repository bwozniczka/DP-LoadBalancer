package com.loadbalancer.balancer;

import java.util.List;


import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.connection.DatabaseConnectionWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//****************************
// Design Pattern: Singleton (Bill Pugh implementation)
//****************************

public class LoadBalancer {
    private static Logger logger = LoggerFactory.getLogger(LoadBalancer.class);
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
        List<DatabaseConnectionWrapper> connections = databaseManager.getConnections();
        logger.info("Number of connections retrieved: " + connections.size());
        return connections;
    }

    // Method to get a database for SELECT operations based on the strategy
    public DatabaseConnectionWrapper getDatabaseConnection() {
        DatabaseConnectionWrapper connection = strategy.chooseDatabase(databaseManager.getConnections());
        logger.info("Chosen database connection: " + connection);
        return connection;
    }
}
package com.loadbalancer.proxy;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.connection.DatabaseConnectionWrapper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import com.loadbalancer.logger.LoggerPanelFactory;

//****************************
// Design Pattern: Proxy
//****************************

public class DatabaseProxy {
    private static final Logger logger = LoggerPanelFactory.getLogger(DatabaseProxy.class);
    
    private LoadBalancer loadBalancer;

    public DatabaseProxy(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public List<String> execute(String query) {
        if (query.toLowerCase().contains("select")) {
            return executeQuery(query);
        } else {
            return executeUpdate(query) ? List.of("Success") : List.of("Failed");
        }
    }

    private boolean executeUpdate(String query) {
        logger.info("Executing update query through proxy: " + query);
        boolean allSucceeded = true;

        for (DatabaseConnectionWrapper connection : loadBalancer.getDatabaseConnections()) {
            if (!connection.executeUpdate(query)) {
                allSucceeded = false;
            }
        }
        return allSucceeded;
    }

    private List<String> executeQuery(String query) {
        logger.info("Executing select query through proxy: " + query);
        DatabaseConnectionWrapper connection = loadBalancer.getDatabaseConnection();
        if (connection == null) {
            logger.error("No available database connections to execute the query.");
            return List.of("No available database connections to execute the query.");
        }
        return connection.executeQuery(query);
    }
}
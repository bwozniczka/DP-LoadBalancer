package com.loadbalancer.proxy;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.connection.DatabaseConnectionWrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//****************************
// Design Pattern: Proxy
//****************************

public class DatabaseProxy {
    private static Logger logger = LoggerFactory.getLogger(DatabaseProxy.class);
    private LoadBalancer loadBalancer;

    public DatabaseProxy(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public boolean executeUpdate(String query) {
        logger.info("Executing update query through proxy: " + query);
        boolean success = true;
        for (DatabaseConnectionWrapper connection : loadBalancer.getDatabaseConnections()) {
            if (!connection.executeUpdate(query)) {
                success = false;
            }
        }
        return success;
    }

    public List<String> executeQuery(String query) {
        logger.info("Executing select query through proxy: " + query);
        DatabaseConnectionWrapper connection = loadBalancer.getDatabaseConnection();
        return connection.executeQuery(query);
    }
}
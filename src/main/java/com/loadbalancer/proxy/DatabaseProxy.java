package com.loadbalancer.proxy;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.connection.DatabaseConnectionWrapper;
import com.loadbalancer.util.Log;

import java.util.List;

//****************************
// Design Pattern: Proxy
//****************************

public class DatabaseProxy {
    private LoadBalancer loadBalancer;

    public DatabaseProxy(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public boolean executeUpdate(String query) {
        Log.info("Executing update query through proxy: " + query);
        boolean success = true;
        for (DatabaseConnectionWrapper connection : loadBalancer.getDatabaseConnections()) {
            if (!connection.executeUpdate(query)) {
                success = false;
            }
        }
        return success;
    }

    public List<String> executeQuery(String query) {
        Log.info("Executing select query through proxy: " + query);
        DatabaseConnectionWrapper connection = loadBalancer.getDatabaseConnection();
        return connection.executeQuery(query);
    }
}
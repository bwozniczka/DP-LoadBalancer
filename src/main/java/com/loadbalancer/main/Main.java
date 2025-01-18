package com.loadbalancer.main;

import java.sql.SQLException;
import java.util.List;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.connection.DatabaseConnectionWrapper;
import com.loadbalancer.util.Log;
import com.loadbalancer.proxy.DatabaseProxy;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();
        databaseFacade.addSampleConnections();
        
        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        loadBalancer.initialize(roundRobinStrategy, databaseFacade);
        Log.info("LoadBalancer initialized successfully.");

        DatabaseProxy databaseProxy = new DatabaseProxy(loadBalancer);

        // ---------------- Below are operations on databases ----------------

        // Clear users table
        databaseProxy.executeUpdate("DELETE FROM Users");

        // Test the load balancer for UPDATING
        databaseProxy.executeUpdate("INSERT INTO Users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com')");

        Thread.sleep(10000);

        // Test the load balancer for UPDATING
        databaseProxy.executeUpdate("INSERT INTO Users (username, password, email) VALUES ('user12', 'pass12', 'user12@example.com')");

        // Test the load balancer for SELECT
        while (true) {
            List<String> entries = databaseProxy.executeQuery("SELECT * FROM Users");
            if (entries != null){
                for (String entry : entries) {
                    System.out.println(entry);
                }
            } else {
                Log.error("Failed to retrieve entries from the database.");
            }

            Thread.sleep(2000);
        }
    }
}

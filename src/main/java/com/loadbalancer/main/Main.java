package com.loadbalancer.main;

import java.sql.SQLException;
import java.util.List;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.connection.DatabaseConnectionWrapper;
import com.loadbalancer.util.Log;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();
        databaseFacade.addSampleConnections();
        
        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        loadBalancer.initialize(roundRobinStrategy, databaseFacade);
        Log.info("LoadBalancer initialized successfully.");

        // Test the load balancer for UPDATING
        loadBalancer.getDatabaseConnections().forEach(connection -> {
            connection.executeUpdate("INSERT INTO Users (username, password, email) VALUES ('userek', 'passwordzik', 'userek@example.com')");
        });

        // Test the load balancer for SELECT
        for (int i = 0; i < 10; i++) {
            DatabaseConnectionWrapper connection = loadBalancer.getDatabaseConnection();
            List<String> entries = connection.executeQuery("SELECT * FROM Users");
            if (entries != null){
                for (String entry : entries) {
                    System.out.println(entry);
                }
            } else {
                Log.error("Failed to retrieve entries from the database.");
            }
        }
    }
}

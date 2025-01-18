package com.loadbalancer.main;

import java.sql.SQLException;
import java.util.List;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.connection.DatabaseConnectionWrapper;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();
        databaseFacade.addSampleConnections();
        
        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        loadBalancer.initialize(roundRobinStrategy, databaseFacade);
        
        for (int i = 0; i < 10; i++) {
            DatabaseConnectionWrapper connection = loadBalancer.getDatabaseConnection();
            List<String> entries = connection.executeQuery("SELECT * FROM Users where id=1");
            for (String entry : entries) {
                System.out.println(entry);
            }
        }
    }
}

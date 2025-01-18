package com.loadbalancer.balancer;

import java.util.List;

import com.loadbalancer.connection.DatabaseConnectionWrapper;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public DatabaseConnectionWrapper chooseDatabase(List<DatabaseConnectionWrapper> databases) {
        if (databases.isEmpty()) {
            throw new IllegalArgumentException("No databases available");
        }

        System.out.println("dbIndex" + currentIndex + ": ");

        DatabaseConnectionWrapper db = databases.get(currentIndex);
        currentIndex = (currentIndex + 1) % databases.size();
        return db;
    }
    
}

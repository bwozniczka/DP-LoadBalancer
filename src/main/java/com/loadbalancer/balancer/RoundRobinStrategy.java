package com.loadbalancer.balancer;

import java.util.List;

import com.loadbalancer.connection.DatabaseConnection;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public DatabaseConnection chooseDatabase(List<DatabaseConnection> databases) {
        if (databases.isEmpty()) {
            throw new IllegalArgumentException("No databases available");
        }

        DatabaseConnection db = databases.get(currentIndex);
        currentIndex = (currentIndex + 1) % databases.size();
        return db;
    }
    
}

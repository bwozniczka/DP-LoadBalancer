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

        
        DatabaseConnectionWrapper db;
        boolean isConnectionValid;
        do {
            db = databases.get(currentIndex);
            isConnectionValid = db.isConnectionValid();

            if (!isConnectionValid) {
                db.tryReconnect();
            }

            System.out.println("Database: " + (currentIndex + 1));
            currentIndex = (currentIndex + 1) % databases.size();

        } while(!isConnectionValid);
        
        return db;
    }
    
}

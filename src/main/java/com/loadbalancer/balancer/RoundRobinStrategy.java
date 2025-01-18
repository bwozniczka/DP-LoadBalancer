package com.loadbalancer.balancer;

import java.sql.Connection;
import java.util.List;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public Connection chooseDatabase(List<Connection> databases) {
        if (databases.isEmpty()) {
            throw new IllegalArgumentException("No databases available");
        }

        System.out.println("dbIndex" + currentIndex + ": ");

        Connection db = databases.get(currentIndex);
        currentIndex = (currentIndex + 1) % databases.size();
        return db;
    }
    
}

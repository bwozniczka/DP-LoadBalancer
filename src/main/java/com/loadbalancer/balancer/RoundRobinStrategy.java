package com.loadbalancer.balancer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.loadbalancer.connection.DatabaseConnectionWrapper;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private static Logger logger = LoggerFactory.getLogger(RoundRobinStrategy.class);
    
    private int currentIndex = 0;

    @Override
    public DatabaseConnectionWrapper chooseDatabase(List<DatabaseConnectionWrapper> databases) {
        if (databases.isEmpty()) {
            throw new IllegalArgumentException("No databases available");
        }

        DatabaseConnectionWrapper db;
        do {
            db = databases.get(currentIndex);
            currentIndex = (currentIndex + 1) % databases.size();

            // if (!db.isUp()) {
            //     db.tryReconnect();
            // }
            
        } while(!db.isUp());
        
        logger.info("Database: " + (currentIndex + 1));
        return db;
    }
    
}

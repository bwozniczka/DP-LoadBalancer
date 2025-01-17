package com.loadbalancer.balancer;

import com.loadbalancer.connection.DatabaseConnection;
import java.util.List;

public interface LoadBalancingStrategy {
    DatabaseConnection chooseDatabase(List<DatabaseConnection> databases);
}
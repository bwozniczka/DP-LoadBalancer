package com.loadbalancer.balancer;

import java.util.List;

import com.loadbalancer.connection.DatabaseConnectionWrapper;

public interface LoadBalancingStrategy {
    DatabaseConnectionWrapper chooseDatabase(List<DatabaseConnectionWrapper> databases);
}
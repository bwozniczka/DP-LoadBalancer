package com.loadbalancer.balancer;

import java.sql.Connection;
import java.util.List;

public interface LoadBalancingStrategy {
    Connection chooseDatabase(List<Connection> databases);
}
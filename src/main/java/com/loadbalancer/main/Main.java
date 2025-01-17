package com.loadbalancer.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.facade.DatabaseConnectionManagerFacade;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();
        databaseFacade.addSampleConnections();
        
        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        loadBalancer.initialize(roundRobinStrategy, databaseFacade);
        
        for (int i = 0; i < 10; i++) {
            Connection connection = loadBalancer.getDatabaseToQuery();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Users");
            
            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                for (int j = 1; j <= columnCount; j++) {
                    System.out.print(resultSet.getString(j) + "\t");
                }
                System.out.println();
            }
        }

    }
}

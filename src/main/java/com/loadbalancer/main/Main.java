package com.loadbalancer.main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.logger.LoggerPanelFactory;
import com.loadbalancer.proxy.DatabaseProxy;

public class Main {
    private static Logger logger = LoggerPanelFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException, InterruptedException {
        DatabaseConnectionManagerFacade databaseFacade = new DatabaseConnectionManagerFacade();
        databaseFacade.addSampleConnections();
        
        RoundRobinStrategy roundRobinStrategy = new RoundRobinStrategy();

        LoadBalancer loadBalancer = LoadBalancer.getInstance();
        loadBalancer.initialize(roundRobinStrategy, databaseFacade);
        logger.info("LoadBalancer initialized successfully.");

        DatabaseProxy databaseProxy = new DatabaseProxy(loadBalancer);

        // // ---------------- Below are operations on databases ----------------
        // databaseProxy.execute("INSERT INTO Users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com')");
        // databaseProxy.execute("DELETE FROM Users");

        // Create and display the main frame
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(databaseFacade, databaseProxy);
            mainFrame.setVisible(true);
        });
    }
}

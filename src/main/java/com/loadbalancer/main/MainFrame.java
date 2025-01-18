package com.loadbalancer.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import com.loadbalancer.balancer.LoadBalancer;
import com.loadbalancer.balancer.RoundRobinStrategy;
import com.loadbalancer.connection.DatabaseConnectionManagerFacade;
import com.loadbalancer.proxy.DatabaseProxy;

public class MainFrame extends JFrame {
    private JTextArea statusArea;
    private JTextArea queryArea;
    private JTextArea resultArea;
    private DatabaseConnectionManagerFacade databaseFacade;
    private DatabaseProxy databaseProxy;

    public MainFrame(DatabaseConnectionManagerFacade databaseFacade, DatabaseProxy databaseProxy) {
        this.databaseFacade = databaseFacade;
        this.databaseProxy = databaseProxy;

        setTitle("Database Load Balancer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusArea = new JTextArea(10, 50);
        statusArea.setEditable(false);
        add(new JScrollPane(statusArea), BorderLayout.NORTH);

        queryArea = new JTextArea(5, 50);
        add(new JScrollPane(queryArea), BorderLayout.CENTER);

        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        JButton executeButton = new JButton("Execute Query");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeQuery();
            }
        });
        add(executeButton, BorderLayout.EAST);

        // Start a background thread to refresh the database status
        Thread statusThread = new Thread(() -> {
            while (true) {
                refreshStatus();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        statusThread.setDaemon(true);
        statusThread.start();
    }

    private void refreshStatus() {
        SwingUtilities.invokeLater(() -> {
            statusArea.setText("Database Status:\n" + databaseFacade.getConnectionStatus());
        });
    }

    private void executeQuery() {
        String query = queryArea.getText();
        List<String> result = databaseProxy.execute(query);
        if (result != null) {
            resultArea.setText(String.join("\n", result));
        } else {
            resultArea.setText("Failed to execute query.");
        }
    }
}
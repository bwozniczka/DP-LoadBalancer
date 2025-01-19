package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;

import com.loadbalancer.logger.LoggerPanelFactory;

import oracle.net.aso.q;

import java.sql.Statement;

public class DatabaseConnectionWrapper {
    private static final Logger logger = LoggerPanelFactory.getLogger(DatabaseConnectionWrapper.class);
    
    private boolean isUp;
    private String recognizableName;
    private Connection connection;
    private Queue<String> queries = new LinkedList<>();
    private String url;
    private String user;
    private String password;

    public DatabaseConnectionWrapper(Connection connection, String recognizableName, String url, String user, String password) {
        this.connection = connection;
        this.url = url;
        this.user = user;
        this.password = password;
        this.recognizableName = recognizableName;
        this.isUp = connection == null ? false : true;
        logger.info("DatabaseConnectionWrapper initialized with connection: " + connection);
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public boolean isUp() {
        return isUp;
    }

    public String getRecognizableName() {
        return recognizableName;
    }

    public boolean tryReconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Reconnected to the " + recognizableName + " successfully.");
            isUp = true;
            executeQueuedQueries();

            return true;
        } catch (SQLException e) {
            logger.error("Can't reconnect (database probably still down): " + recognizableName);
            return false;
        }
    }

    // Disconnects the connection
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Connection closed successfully.");
            }
        } catch (SQLException e) {
            logger.error("Error while closing connection: " + e.getMessage());
        }
    }

    public boolean isConnectionValid(){
        try {
            if (connection == null){
                return false;
            }

            return connection.isValid(0);
        } catch (SQLException e) {
            logger.error("Error checking connection validity: " + e.getMessage());
            isUp = false;
            return false;
        }
    }

    public boolean executeUpdate(String query) {

        if (!isUp) {
            queries.add(query);

            // make sense?
            return true;
        }

        // execute actual query
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            logger.info("Database: " + recognizableName + ": Query executed successfully: " + query);
        } catch (SQLException e) {
            // prevent the situation when db get "down", but background thread haven't reach 5s interval to mark it.
            if (isConnectionValid()){
                queries.add(query);
            } else {
                isUp = false;
                queries.add(query);
            }

            logger.error("Query failed: " + query + ". Error: " + e.getMessage());
            return false;
        }

        return true;
    }

    public List<String> executeQuery(String query) {
        logger.info("Executing query: " + query + " on database " + recognizableName);

        // execute actual query
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            int columnCount = resultSet.getMetaData().getColumnCount();
            List<String> output = new ArrayList<String>();

            while (resultSet.next()) {
                String record = "";
                for (int j = 1; j <= columnCount; j++) {
                    record += (resultSet.getString(j) + "\t");
                }

                output.add(record);
            }

            logger.info("Query executed successfully");
            return output;

        } catch (SQLException e) {
            logger.error("Query failed: " + query + ". Error: " + e.getMessage());
            return null;
        }
    }

    private void executeQueuedQueries(){
        while (!queries.isEmpty()) {
            String query = queries.poll();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                logger.info("Queued query executed: " + query);
            } catch (SQLException e) {
                logger.error("Error executing queued query: " + query + ". Error: " + e.getMessage());
            }
        }
    }

    public String getDatabaseName() {
        return recognizableName;
    }

    public Connection getConnection() {
        return connection;
    }
}

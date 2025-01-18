package com.loadbalancer.connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//****************************
// Design Pattern: Facade
// Facade - Expose an easy and clean interface for complex code operations
//****************************

public class DatabaseConnectionManagerFacade {
    private static Logger logger = LoggerFactory.getLogger(DatabaseConnectionManagerFacade.class);
    
    private List<DatabaseConnectionWrapper> connections;
    private ScheduledExecutorService scheduler;

    public DatabaseConnectionManagerFacade() {
        connections = new ArrayList<>();

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::checkConnections, 10L, 10L, TimeUnit.SECONDS);
    }

    private void checkConnections() {
        for (DatabaseConnectionWrapper connection : connections) {
            boolean isValid = connection.isConnectionValid();
            connection.setIsUp(isValid);
            logger.info("Connection " + connection + " is " + (isValid ? "up" : "down"));

            if (!isValid) {
                connection.tryReconnect();
            }
        }
    }

    // mock function
    public void addSampleConnections() throws SQLException {
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5432, "db1", "user1", "password1");
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5433, "db2", "user2", "password2");
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5434, "db3", "user3", "password3");
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5435, "db4", "user4", "password4");
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5436, "db5", "user5", "password5");
        addConnection(DatabaseType.MYSQL, "localhost", 3306, "db6", "user6", "password6");
        addConnection(DatabaseType.MYSQL, "localhost", 3307, "db7", "user7", "password7");
        addConnection(DatabaseType.MYSQL, "localhost", 3308, "db8", "user8", "password8");
        addConnection(DatabaseType.MYSQL, "localhost", 3309, "db9", "user9", "password9");
        addConnection(DatabaseType.MYSQL, "localhost", 3310, "db10", "user10", "password10");
        logger.info("Sample connections added successfully.");
    }

    public void addConnection(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        DatabaseConnectionWrapper connection = DatabaseConnectionFactory.createConnection(type, host, port, dbName, user, password);

        if (connection != null){
            connections.add(connection);
            logger.info("Connection to " + dbName + " added successfully.");
        } else {
            logger.error("Failed to add connection to " + dbName);
        }

    }

    /*
     * Close connection + remove it from a list.
     * True when connection get closed and removed. Otherwise the element was not present in the list.
     */
    public boolean disposeConnection(DatabaseConnectionWrapper connection) {
        boolean removed = false;

        if (connection != null) {
            logger.info("Disconnecting and removing connection: " + connection);
            connection.disconnect();
            removed = connections.remove(connection);
            if (removed) {
                logger.info("Connection " + connection + " successfully removed.");
            } else {
                logger.warn("Connection " + connection + " was not found in the list.");
            }
        }
        return removed;
    }

    public void disposeConnectionAll() {
        logger.info("Disposing all database connections...");
        for (DatabaseConnectionWrapper connection : connections) {
            if (connection != null) {
                logger.info("Disconnecting and removing connection: " + connection);
                connection.disconnect();
                connections.remove(connection);
            }
        }
        logger.info("All connections disposed.");
    }

    public List<DatabaseConnectionWrapper> getConnections() {
        return connections;
    }

    public String getConnectionStatus() {
        StringBuilder status = new StringBuilder();
        for (DatabaseConnectionWrapper connection : connections) {
            status.append(connection.getRecognizableName())
                  .append(" - ")
                  .append(connection.isUp() ? "up" : "down")
                  .append("\n");
        }
        return status.toString();
    }
}
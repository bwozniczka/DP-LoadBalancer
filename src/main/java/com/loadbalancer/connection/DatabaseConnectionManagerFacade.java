package com.loadbalancer.connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.loadbalancer.util.Log;

//****************************
// Design Pattern: Facade
// Facade - Expose an easy and clean interface for complex code operations
//****************************

public class DatabaseConnectionManagerFacade {
    private List<DatabaseConnectionWrapper> connections;

    public DatabaseConnectionManagerFacade() {
        connections = new ArrayList<>();
    }

    // mock function
    public void addSampleConnections() throws SQLException {
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5432, "db1", "user1", "password1");
        addConnection(DatabaseType.POSTGRESQL, "localhost", 5433, "db2", "user2", "password2");
        addConnection(DatabaseType.MYSQL, "localhost", 3306, "db3", "user3", "password3");
        Log.info("Sample connections added successfully.");
    }

    public void addConnection(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        connections.add(DatabaseConnectionFactory.createConnection(type, host, port, dbName, user, password));

        Log.info("Connection to " + dbName + " added successfully.");
    }

    /*
     * Close connection + remove it from a list.
     * True when connection get closed and removed. Otherwise the element was not present in the list.
     */
    public boolean disposeConnection(DatabaseConnectionWrapper connection) {
        boolean removed = false;

        if (connection != null) {
            Log.info("Disconnecting and removing connection: " + connection);
            connection.disconnect();
            removed = connections.remove(connection);
            if (removed) {
                Log.info("Connection " + connection + " successfully removed.");
            } else {
                Log.warn("Connection " + connection + " was not found in the list.");
            }
        }
        return removed;
    }

    public void disposeConnectionAll() {
        Log.info("Disposing all database connections...");
        for (DatabaseConnectionWrapper connection : connections) {
            if (connection != null) {
                Log.info("Disconnecting and removing connection: " + connection);
                connection.disconnect();
                connections.remove(connection);
            }
        }
        Log.info("All connections disposed.");
    }

    public List<DatabaseConnectionWrapper> getConnections() {
        return connections;
    }
}
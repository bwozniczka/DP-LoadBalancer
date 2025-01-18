package com.loadbalancer.connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    }

    public void addConnection(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        connections.add(DatabaseConnectionFactory.createConnection(type, host, port, dbName, user, password));
    }

    /*
     * True when connection get closed and removed. Otherwise the element was not present in the list.
     */
    public boolean disposeConnection(DatabaseConnectionWrapper connection) {
        boolean removed = false;

        if (connection != null) {
            connection.disconnect();
            removed = connections.remove(connection);
        }

        return removed;
    }

    public void disposeConnectionAll(){
        for (DatabaseConnectionWrapper connection : connections) {
            if (connection != null) {
                connection.disconnect();
                connections.remove(connection);
            }
        }
    }

    public List<DatabaseConnectionWrapper> getConnections() {
        return connections;
    }
}
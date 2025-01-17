package com.loadbalancer.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.loadbalancer.factory.DatabaseConnectionFactory;
import com.loadbalancer.util.DatabaseType;

//****************************
// Design Pattern: Facade
//****************************

public class DatabaseConnectionManagerFacade {
    private List<Connection> connections;

    public DatabaseConnectionManagerFacade() {
        connections = new ArrayList<>();
    }

    // mock function
    public void addSampleConnections() throws SQLException {
        connections.add(connect(DatabaseType.POSTGRESQL, "localhost", 5432, "db1", "user1", "password1"));
        connections.add(connect(DatabaseType.POSTGRESQL, "localhost", 5433, "db2", "user2", "password2"));
        connections.add(connect(DatabaseType.MYSQL, "localhost", 3306, "db3", "user3", "password3"));
    }

    public Connection connect(DatabaseType type, String host, int port, String dbName, String user, String password) throws SQLException {
        return DatabaseConnectionFactory.createConnection(type, host, port, dbName, user, password);
    }

    public void disconnect() {
        for (Connection connection : connections) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.sql.Statement;
import com.loadbalancer.util.Log;

public class DatabaseConnectionWrapper {
    private Connection connection;
    private Queue<String> queries = new LinkedList<>();
    private String url;
    private String user;
    private String password;

    public DatabaseConnectionWrapper(Connection connection, String url, String user, String password) {
        this.connection = connection;
        this.url = url;
        this.user = user;
        this.password = password;
        Log.info("DatabaseConnectionWrapper initialized with connection: " + connection);
    }

    public boolean tryReconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = DriverManager.getConnection(url, user, password);
            Log.info("Reconnected to the database successfully.");

            return true;
        } catch (SQLException e) {
            Log.error("Can't reconnect (database probably still down): " + e.getMessage());
            return false;
        }
    }

    // Disconnects the connection
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Log.info("Connection closed successfully.");
            }
        } catch (SQLException e) {
            Log.error("Error while closing connection: " + e.getMessage());
        }
    }

    public boolean isConnectionValid(){
        try {
            return connection.isValid(0);
        } catch (SQLException e) {
            Log.error("Error checking connection validity: " + e.getMessage());
            return false;
        }
    }

    /*
    * Returns false when the connection is not valid. Connection is queued.
    */
    public boolean executeUpdate(String query) {

        // check whether connectio is valid
        try {
            if (!connection.isValid(0)){
                Log.error("Connection is not valid, query will be queued: " + query);
                queries.add(query);
                return false;
            }
        } catch (SQLException e) {
            Log.error("Error checking connection validity: " + e.getMessage());
            e.printStackTrace();
        }

        // execute queued queries
        executeQueuedQueries();
        if (!queries.isEmpty()){
            queries.add(query);
            Log.warn("Couldn't execute queries in queue, therefore current query is also appended: " + query);
            return false;
        }

        // execute actual query
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            Log.info("Query executed successfully: " + query);
        } catch (SQLException e) {
            Log.error("Query failed: " + query + ". Error: " + e.getMessage());
        }

        return true;
    }

    /*
     * Executes a SELECT query and returns the ResultSet.
     * If the query fails, it returns null.
     */
    public List<String> executeQuery(String query) {
        Log.info("Executing query: " + query);

        // check whether connectio is valid
        try {
            if (!connection.isValid(0)){
                Log.error("Connection is not valid");
                return null;
            }
        } catch (SQLException e) {
            Log.error("Error checking connection validity: " + e.getMessage());
            e.printStackTrace();
        }

        // execute queued queries
        executeQueuedQueries();
        if (!queries.isEmpty()){
            Log.warn("Couldn't execute queries in queue: " + query);
            return null;
        }

        // execute actual query
        try (Statement statement = connection.createStatement()) {
            Log.info("Executing query: " + query);

            ResultSet resultSet = statement.executeQuery(query);
            int columnCount = resultSet.getMetaData().getColumnCount();
            List<String> output = new ArrayList<String>();

            while (resultSet.next()) {
                String record = "";
                for (int j = 1; j <= columnCount; j++) {
                    record += (resultSet.getString(j) + "\t");
                }
                record += '\n';

                output.add(record);
            }

            Log.info("Query executed successfully");
            return output;

        } catch (SQLException e) {
            Log.error("Query failed: " + query + ". Error: " + e.getMessage());
            return null;
        }
    }

    private void executeQueuedQueries(){
        while (!queries.isEmpty()) {
            String query = queries.poll();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                Log.info("Queued query executed: " + query);
            } catch (SQLException e) {
                Log.error("Error executing queued query: " + query + ". Error: " + e.getMessage());
            }
        }
    }
}

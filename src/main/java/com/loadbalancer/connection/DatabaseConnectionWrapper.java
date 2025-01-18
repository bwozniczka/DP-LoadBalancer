package com.loadbalancer.connection;

import java.sql.Connection;
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
    private Queue<String> queries = new LinkedList<String>();

    public DatabaseConnectionWrapper(Connection connection) {
        this.connection = connection;
        Log.info("DatabaseConnectionWrapper initialized with connection: " + connection);
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

    /*
    * Executes a query that alters the database (e.g., INSERT, UPDATE, DELETE).
    * If the query fails, it is added to the queries queue.
    */
    public boolean executeUpdate(String query) {
        executeQueuedQueries();
        if (!queries.isEmpty()){
            queries.add(query);
            Log.warn("Query failed and added to queue: " + query);
            return false;
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            Log.info("Query executed successfully: " + query);
            return true;
        } catch (SQLException e) {
            queries.add(query);
            Log.error("Query failed, added to queue: " + query + ". Error: " + e.getMessage());
            return false;
        }
    }

    /*
     * Executes a SELECT query and returns the ResultSet.
     * If the query fails, it returns null.
     */
    public List<String> executeQuery(String query) {
        Log.info("Executing query: " + query);

        executeQueuedQueries();
        if (!queries.isEmpty()){
            Log.warn("Query added to queue due to failure: " + query);
            return null;
        }

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

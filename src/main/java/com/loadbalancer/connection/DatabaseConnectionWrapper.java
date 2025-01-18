package com.loadbalancer.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.sql.Statement;

public class DatabaseConnectionWrapper {
    private Connection connection;
    private Queue<String> queries = new LinkedList<String>();

    public DatabaseConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    // public void connect() {

    // }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Executes a query that alters the database (e.g., INSERT, UPDATE, DELETE).
    * If the query fails, it is added to the queries queue.
    */
    public boolean executeUpdate(String query) {
        executeQueuedQuries();
        if (!queries.isEmpty()){
            queries.add(query);
            return false;
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            queries.add(query);
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Executes a SELECT query and returns the ResultSet.
     * If the query fails, it returns null.
     */
    public List<String> executeQuery(String query) {
        System.out.println("Executing query: " + query);

        executeQueuedQuries();
        if (!queries.isEmpty()){
            System.out.println("Query added to queue: " + query);
            return null;
        }

        try (Statement statement = connection.createStatement()) {
            System.out.println("Query executed: " + query);

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

            return output;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Query failed: " + query);
            return null;
        }
    }

    private void executeQueuedQuries(){
        while (!queries.isEmpty()) {
            String query = queries.poll();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
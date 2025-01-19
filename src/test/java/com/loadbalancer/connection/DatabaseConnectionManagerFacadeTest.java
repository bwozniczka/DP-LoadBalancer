package com.loadbalancer.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseConnectionManagerFacadeTest {

    private DatabaseConnectionManagerFacade manager;

    @Mock
    private DatabaseConnectionWrapper mockConnection;

    @BeforeEach
    void setUp() {
        manager = new DatabaseConnectionManagerFacade();
    }


    @Test
    void testGetConnectionStatus() {
        when(mockConnection.getRecognizableName()).thenReturn("TestDB");
        when(mockConnection.isUp()).thenReturn(true);
        manager.getConnections().add(mockConnection);

        String status = manager.getConnectionStatus();

        assertTrue(status.contains("TestDB"));
        assertTrue(status.contains("up"));
    }

    @Test
    void testCheckConnections() throws InterruptedException {
        when(mockConnection.isConnectionValid()).thenReturn(false);
        manager.getConnections().add(mockConnection);

        Thread.sleep(11000);

        verify(mockConnection, atLeastOnce()).isConnectionValid();
        verify(mockConnection, atLeastOnce()).tryReconnect();
    }

    @Test
    void testAddConnectionWithFailure() throws SQLException {
        DatabaseType type = DatabaseType.POSTGRESQL;
        String host = "invalid-host";
        int port = 5432;
        String dbName = "invalid-db";
        String user = "invalid-user";
        String password = "invalid-pass";

        manager.addConnection(type, host, port, dbName, user, password);

        List<DatabaseConnectionWrapper> connections = manager.getConnections();
        assertFalse(connections.isEmpty());
    }

    @Test
    void testDisposeNullConnection() {
        boolean result = manager.disposeConnection(null);

        assertFalse(result);
    }

    @Test
    void testDisposeNonExistentConnection() {
        DatabaseConnectionWrapper nonExistentConnection = mock(DatabaseConnectionWrapper.class);

        boolean result = manager.disposeConnection(nonExistentConnection);

        assertFalse(result);
        verify(nonExistentConnection).disconnect();
    }
}
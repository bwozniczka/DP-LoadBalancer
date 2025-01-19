package com.loadbalancer.connection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionFactoryTest {

    @Test
    void testConnectionFailure() {
        // Test with invalid credentials
        DatabaseConnectionWrapper result = DatabaseConnectionFactory.createConnection(
                DatabaseType.MYSQL,
                "invalid-host",
                3306,
                "testdb",
                "invalid-user",
                "invalid-password"
        );

        assertNull(result);
    }
}
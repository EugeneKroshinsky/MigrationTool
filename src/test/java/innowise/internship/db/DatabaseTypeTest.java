package innowise.internship.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DatabaseTypeTest {
    private static Connection connection;
    private static DatabaseMetaData metaData;
    @BeforeAll
    static void setUp() {
        connection = Mockito.mock(Connection.class);
        metaData = Mockito.mock(DatabaseMetaData.class);
        try {
            when(connection.getMetaData()).thenReturn(metaData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void fromConnectionMysqlTest() {
        try {
            when(metaData.getDatabaseProductName()).thenReturn("mysql");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DatabaseType databaseType = DatabaseType.fromConnection(connection);
        assertEquals(DatabaseType.MYSQL, databaseType);
    }

    @Test
    void fromConnectionWrongValueTest() {
        try {
            when(metaData.getDatabaseProductName()).thenReturn("wrong value");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DatabaseType databaseType = DatabaseType.fromConnection(connection);
        assertEquals(DatabaseType.UNKNOWN, databaseType);
    }
}
package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MigrationManagerTest {

    private MigrationManager migrationManager; // Класс, содержащий метод getNewMigrations
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private List<FileInfo> files;

    @BeforeEach
     void setUp() throws SQLException {

        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(Mockito.anyString())).thenReturn(resultSet);
        Mockito.when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        migrationManager = new MigrationManager(connection);
        when(resultSet.getString(Mockito.anyString())).thenReturn("1.0");
        List<FileInfo> migrationFiles = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo();
        fileInfo1.setVersion("1.0");
        migrationFiles.add(fileInfo1);
        FileInfo fileInfo2 = new FileInfo();
        fileInfo2.setVersion("1.1");
        migrationFiles.add(fileInfo2);
        files = migrationManager.getNewMigrations(migrationFiles);
    }

    @Test
    void getMigrationsSizeTest() throws SQLException {
        assertEquals(1, files.size());
    }
    @Test
    void getMigrationsValueTest() throws SQLException {
        assertEquals("1.1", files.get(0).getVersion());
    }
}
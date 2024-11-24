package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.migrations.MigrationManager;
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
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        migrationManager = new MigrationManager(connection);
        when(resultSet.getString(Mockito.anyString())).thenReturn("1.0");
        List<FileInfo> migrationFiles = getMigrationFiles();
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

    private List<FileInfo> getMigrationFiles() {
        List<FileInfo> migrationFiles = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setVersion("1.0");
        migrationFiles.add(fileInfo);
        fileInfo = new FileInfo();
        fileInfo.setVersion("1.1");
        migrationFiles.add(fileInfo);
        return migrationFiles;
    }
}
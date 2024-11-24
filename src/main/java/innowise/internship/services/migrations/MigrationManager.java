package innowise.internship.services.migrations;

import innowise.internship.dto.FileInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class MigrationManager {
    private Connection connection;
    private static final String GET_VERSION = "SELECT version FROM migration_history";

    public List<FileInfo> getNewMigrations(List<FileInfo> migrationFiles) {
        log.info("Search new migration files");
        findNewMigrations(migrationFiles);
        log.info("New migration files have been found: {}", migrationFiles.size());
        return migrationFiles;
    }
    private void findNewMigrations(List<FileInfo> migrationFiles) {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_VERSION);
            while (resultSet.next()) {
                String version = resultSet.getString("version");
                migrationFiles.removeIf(file -> file.getVersion().equals(version));
            }
        } catch (SQLException e) {
            log.error("Failed to get new migration files", e);
        }
    }
}

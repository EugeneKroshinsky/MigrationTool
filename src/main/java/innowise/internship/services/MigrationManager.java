package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
public class MigrationManager {
    private Connection connection = ConnectionManager.getConnection();

    public List<FileInfo> getNewMigrations(List<FileInfo> migrationFiles) {
        log.info("Search new migration files");
        try(Statement statement = connection.createStatement()) {
            String sql = "SELECT version FROM migration_history";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String version = resultSet.getString("version");
                migrationFiles.removeIf(file -> file.getVersion().equals(version));
            }

        } catch (SQLException e) {
            log.error("Failed to get new migration files", e);
        }
        log.info("New migration files have been found: {}", migrationFiles.size());
        return migrationFiles;
    }
}

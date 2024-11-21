package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MigrationManager {
    private MigrationFileReader migrationFileReader;
    private Connection connection = ConnectionManager.getConnection();

    public List<FileInfo> getNewMigrations() {
        log.info("Search new migration files");
        Properties properties = PropertiesUtils.getProperties("application.properties");
        migrationFileReader = new MigrationFileClasspathReader(properties);
        List<FileInfo> migrationFiles = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            migrationFiles = migrationFileReader.getMigrationFiles();
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

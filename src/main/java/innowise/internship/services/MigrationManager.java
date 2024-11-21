package innowise.internship.services;

import innowise.internship.dto.FileInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MigrationManager {
    private MigrationFileReader migrationFileReader;
    private Connection connection;

    public List<FileInfo> getNewMigrations() {
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
            e.printStackTrace();
        }
        return migrationFiles;
    }
}

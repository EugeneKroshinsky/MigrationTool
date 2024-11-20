package innowise.internship.services;

import innowise.internship.dto.FileInfo;

import java.sql.Connection;
import java.util.List;

public class MigrationManager {
    private MigrationFileReader migrationFileReader;
    private Connection connection;

    public List<FileInfo> getNewMigrations() {
        return null;
    }
}

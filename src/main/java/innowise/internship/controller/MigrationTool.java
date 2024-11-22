package innowise.internship.controller;

import innowise.internship.db.ConnectionManager;
import innowise.internship.db.DatabaseType;
import innowise.internship.dto.FileInfo;
import innowise.internship.services.*;
import innowise.internship.utils.PropertiesUtils;
import innowise.internship.utils.SqlFileUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MigrationTool {
    private static final Properties properties;
    private static final MigrationFileReader migrationFileReader;
    private static final MigrationManager migrationManager;
    private static final SqlFileUtil SQL_FILE_UTIL;
    private static final MigrationExecutor migrationExecutor;
    private static final DatabaseType databaseType;
    private static Connection connection;

    static {
        log.info("MigrationTool start initialization");
        properties = PropertiesUtils.getProperties("application.properties");
        SQL_FILE_UTIL = new SqlFileUtil();
        connection = ConnectionManager.getConnection();
        databaseType = DatabaseType.fromConnection(connection);
        migrationManager = new MigrationManager(connection);
        migrationFileReader = new MigrationFileClasspathReader(properties);
        migrationExecutor = new MigrationExecutor(connection, SQL_FILE_UTIL, databaseType);
        log.info("MigrationTool finish initialization");
    }

    public static void run (){
        log.info("MigrationTool start run");
        migrationExecutor.createMigrationTableIfNotExist();
        migrationExecutor.createConcurrencyTableIfNotExist();
        List<FileInfo> migrationFiles = migrationFileReader.getMigrationFiles();
        List<FileInfo> migrationFilesToExecute = migrationManager.getNewMigrations(migrationFiles);
        if (!migrationFilesToExecute.isEmpty()) {
            executeMigrations(migrationFilesToExecute);
        }
        log.info("MigrationTool finish run");
    }

    private static void executeMigrations(List<FileInfo> migrationFiles) {
            migrationExecutor.lockDatabase();
            migrationExecutor.executeMigration(migrationFiles);
            migrationExecutor.unlockDatabase();
    }
}

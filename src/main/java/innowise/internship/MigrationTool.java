package innowise.internship;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.*;
import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MigrationTool {
    private static final Properties propeties;
    private static final MigrationFileReader migrationFileReader;
    private static final MigrationManager migrationManager;
    private static final SQLReader sqlReader;
    private static final MigrationExecutor migrationExecutor;
    private static Connection connection;

    static {
        log.info("MigrationTool start initialization");
        propeties = PropertiesUtils.getProperties("application.properties");
        connection = ConnectionManager.getConnection();
        migrationFileReader = new MigrationFileClasspathReader(propeties);
        migrationManager = new MigrationManager();
        sqlReader = new SQLReader();
        migrationExecutor = new MigrationExecutor(connection, sqlReader, propeties);
        log.info("MigrationTool finish initialization");
    }

    public static void run (){
        log.info("MigrationTool start run");
        List<FileInfo> migrationFiles = migrationFileReader.getMigrationFiles();
        List<FileInfo> migrationFileToExecute = migrationManager.getNewMigrations(migrationFiles);
        executeMigrations(migrationFileToExecute);
        log.info("MigrationTool finish run");
    }

    private static void executeMigrations(List<FileInfo> migrationFileToExecute) {
        migrationExecutor.createMigrationTableIfNotExist();
        migrationExecutor.createConcurrencyTableIfNotExist();
        migrationExecutor.lockDatabase();
        migrationExecutor.executeMigration(migrationFileToExecute);
        migrationExecutor.unlockDatabase();
    }
}

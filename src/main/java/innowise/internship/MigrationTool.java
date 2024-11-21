package innowise.internship;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.MigrationExecutor;
import innowise.internship.services.MigrationFileClasspathReader;
import innowise.internship.services.MigrationFileReader;
import innowise.internship.services.MigrationManager;
import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MigrationTool {
    public static void run (){
        log.info("MigrationTool started");
        Properties properties = PropertiesUtils.getProperties("application.properties");
        MigrationFileReader migrationFileReader = new MigrationFileClasspathReader(properties);
        MigrationManager migrationManager = new MigrationManager();
        List<FileInfo> migrationFiles = migrationFileReader.getMigrationFiles();
        MigrationExecutor migrationExecutor = new MigrationExecutor();
        migrationExecutor.createMigrationTableIfNotExist();
        migrationExecutor.createConcurrencyTableIfNotExist();
        migrationExecutor.lockDatabase();
        List<FileInfo> migrationFileToExecute = migrationManager.getNewMigrations(migrationFiles);
        migrationExecutor.executeMigration(migrationFileToExecute);
        migrationExecutor.unlockDatabase();
    }
}

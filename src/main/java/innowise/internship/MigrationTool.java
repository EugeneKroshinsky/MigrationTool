package innowise.internship;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.MigrationExecutor;
import innowise.internship.services.MigrationFileClasspathReader;
import innowise.internship.services.MigrationFileReader;
import innowise.internship.utils.PropertiesUtils;

import java.util.List;
import java.util.Properties;

public class MigrationTool {
    public static void run (){
        Properties properties = PropertiesUtils.getProperties("application.properties");
        MigrationFileReader migrationFileReader = new MigrationFileClasspathReader(properties);
        List<FileInfo> migrationFiles = migrationFileReader.getMigrationFiles();
        MigrationExecutor migrationExecutor = new MigrationExecutor();
        migrationExecutor.createMigrationTableIfNotExist();
        migrationExecutor.createConcurrencyTableIfNotExist();
        //migrationExecutor.executeMigration(migrationFiles);
    }
}

package innowise.internship;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.ConnectionManager;
import innowise.internship.services.MigrationExecutor;
import innowise.internship.services.MigrationFileClasspathReader;
import innowise.internship.services.MigrationFileReader;
import innowise.internship.utils.PropertiesUtils;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;

public class MigrationTool {
    public static void run (){
        MigrationFileReader migrationFileReader
                = new MigrationFileClasspathReader(PropertiesUtils.getProperties("application.properties"));
        List<FileInfo> migrationFiles = migrationFileReader.getMigrationFiles();
        MigrationExecutor migrationExecutor = new MigrationExecutor();
        migrationExecutor.createMigrationTableIfNotExist();
        migrationExecutor.createConcurrencyTableIfNotExist();
        Connection connection = ConnectionManager.getConnection();
    }
}

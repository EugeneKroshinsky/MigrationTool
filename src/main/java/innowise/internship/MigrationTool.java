package innowise.internship;

import innowise.internship.utils.PropertiesUtils;

import java.sql.Connection;
import java.util.List;

public class MigrationTool {
    public static void run (){
        MigrationFileReader migrationFileReader
                = new MigrationFileClasspathReader(PropertiesUtils.getProperties("application.properties"));
        List<String> migrationFiles = migrationFileReader.getMigrationFiles();
        migrationFiles.forEach(System.out::println);
        Connection connection = ConnectionManager.getConnection();
    }
}

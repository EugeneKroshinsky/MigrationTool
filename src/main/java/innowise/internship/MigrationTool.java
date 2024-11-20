package innowise.internship;

import innowise.internship.dto.FileInfo;
import innowise.internship.services.ConnectionManager;
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
        migrationFiles.forEach(System.out::println);
  /*      FileInfo.getVersion(migrationFiles.get(0));
        FileInfo.getDescription(migrationFiles.get(0));
        FileInfo.getActionType(migrationFiles.get(0));
        FileInfo.getType(migrationFiles.get(0));*/
        Connection connection = ConnectionManager.getConnection();
    }
}

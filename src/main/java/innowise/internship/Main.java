package innowise.internship;

import innowise.internship.utils.PropertiesUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PropertiesUtils propertiesUtils = new PropertiesUtils("application.properties");
        MigrationFileReader migrationFileReader
                = new MigrationFileClasspathReader(propertiesUtils.getProperty("filepath"));
        List<String> migrationFiles = migrationFileReader.getMigrationFiles();
        migrationFiles.forEach(System.out::println);
        MigrationTool.run();
    }
}
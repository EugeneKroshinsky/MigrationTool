package innowise.internship;

import innowise.internship.utils.PropertiesUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MigrationFileReader migrationFileReader
                = new MigrationFileClasspathReader(PropertiesUtils.getProperties("filepath"));
        List<String> migrationFiles = migrationFileReader.getMigrationFiles();
        migrationFiles.forEach(System.out::println);
        MigrationTool.run();
    }
}
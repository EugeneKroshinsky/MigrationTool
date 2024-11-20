package innowise.internship.services;

import innowise.internship.dto.FileInfo;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MigrationFileClasspathReader implements MigrationFileReader {
    private final Properties properties;

    public MigrationFileClasspathReader(Properties properties) {
        this.properties = properties;
    }

    @Override
    public List<FileInfo> getMigrationFiles() {
        List<FileInfo> migrationFiles = new ArrayList<>();
        try {
            String resourcePath = properties.getProperty("filepath");
            URL resource = getClass().getClassLoader().getResource(resourcePath);
            if (resource == null) {
                throw new IllegalArgumentException("Resource path not found: " + resourcePath);
            }

            Path path = Paths.get(resource.toURI());
            Files.walk(path)
                    .filter(pth -> pth.toString().endsWith(".sql"))
                    .sorted()
                    .map(FileInfo::new)
                    .filter(FileInfo::isCorrect)
                    .forEach(migrationFiles::add);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load migration files", e);
        }
        return migrationFiles;
    }
}



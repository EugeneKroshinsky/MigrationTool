package innowise.internship.services.migrations;

import innowise.internship.factories.FileInfoFactory;
import innowise.internship.dto.FileInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@AllArgsConstructor
public class MigrationFileClasspathReader implements MigrationFileReader {
    private final Properties properties;

    @Override
    public List<FileInfo> getMigrationFiles() {
        log.info("Search migration files");
        URL resource = getResource();
        List<FileInfo> migrationFiles = createListOfMigrations(resource);
        log.info("Migration files have been found: {}", migrationFiles.size());
        return migrationFiles;
    }
    private URL getResource() {
        String resourcePath = properties.getProperty("filepath");
        try {
            URL resource = getClass().getClassLoader().getResource(resourcePath);
            return resource;
        } catch (NullPointerException e) {
            log.error("Failed to load migration files", e);
            throw new RuntimeException("Failed to load migration files", e);
        }
    }
    private List<FileInfo> createListOfMigrations(URL resource) {
        List<FileInfo> migrationFiles = new ArrayList<>();
        try {
            Path path = Paths.get(resource.toURI());
            Files.walk(path)
                    .filter(pth -> pth.toString().endsWith(".sql"))
                    .map(FileInfoFactory::createFileInfo)
                    .filter(FileInfo::isCorrect)
                    .sorted()
                    .forEach(migrationFiles::add);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            log.error("Failed to load migration files, maybe you didn't declare folder", e);
            throw new RuntimeException("Failed to load migration files", e);
        }
        return migrationFiles;
    }

}



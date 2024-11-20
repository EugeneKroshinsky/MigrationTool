package innowise.internship;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MigrationFileClasspathReader implements MigrationFileReader {
    private final String resourcePath;

    public MigrationFileClasspathReader(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public List<String> getMigrationFiles() {
        List<String> migrationFiles = new ArrayList<>();
        try {
            URL resource = getClass().getClassLoader().getResource(resourcePath);
            if (resource == null) {
                throw new IllegalArgumentException("Resource path not found: " + resourcePath);
            }

            Path path = Paths.get(resource.toURI());
            Files.walk(path)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".sql"))
                    .sorted()
                    .forEach(migrationFiles::add);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load migration files", e);
        }
        return migrationFiles;
    }
}


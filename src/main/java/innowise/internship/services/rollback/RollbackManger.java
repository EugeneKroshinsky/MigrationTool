package innowise.internship.services.rollback;

import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class RollbackManger {
    private static final Properties properties
            = PropertiesUtils.getProperties("application.properties");
    private static final String GET_VERSION = "SELECT version FROM migration_history";
    public List<String> getNewMigrations(List<String> migrationFiles) {
        log.info("Search new migration files");
        int count;
        try {
            count = Integer.parseInt(properties.getProperty("count"));
        } catch (NumberFormatException e) {
            log.error("Failed to get count of migration files", e);
            throw new RuntimeException("Failed to get count of migration files");
        }
        log.info("New migration files have been found: {}", migrationFiles.size());
        return findNewMigrations(migrationFiles, count);
    }
    private List<String> findNewMigrations(List<String> migrationFiles, int count) {
        List<String> result = new ArrayList<>();
        for (int i = migrationFiles.size() - 1; i >= migrationFiles.size() - count; i--) {
            result.add(migrationFiles.get(i));
        }
        return result;
    }
}

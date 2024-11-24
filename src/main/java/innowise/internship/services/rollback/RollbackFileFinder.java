package innowise.internship.services.rollback;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class RollbackFileFinder {
    private Connection connection;
    private static final String GET_VERSION = "SELECT version FROM migration_history";
    public List<String> getAllExecutedVersions() {
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_VERSION);
            List<String> versions = new ArrayList<>();
            while (resultSet.next()) {
                versions.add(resultSet.getString("version"));
            }
            return versions;
        } catch (SQLException e) {
            log.error("Failed to get executed versions", e);
            throw new RuntimeException("Failed to get executed versions");
        }
    }
}

package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MigrationExecutor {
    private Connection connection = ConnectionManager.getConnection();
    private final SQLReader sqlReader = new SQLReader();
    private final Properties properties = PropertiesUtils.getProperties("application.properties");

    public void createMigrationTableIfNotExist() {
        log.info("Creating migration table if not exist");
        Path path = Paths.get(properties.getProperty("concurrency.script.path"));
        String sqlFile = sqlReader.read(path);
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlFile);
        } catch (SQLException e) {
            log.error("Failed to create migration table", e);
        }
    }
    public void createConcurrencyTableIfNotExist() {
        log.info("Creating concurrency table if not exist");
        Path path = Paths.get(properties.getProperty("migration.script.path"));
        String sqlFile = sqlReader.read(path);
        try(Statement statement = connection.createStatement()) {
            statement.execute(sqlFile);
        } catch (SQLException e) {
            log.error("Failed to create concurrency table", e);
        }
    }
    public void executeMigration(List<FileInfo> migrationFiles) {
        String PREPARED = properties.getProperty("prepared.statement.migration.history");
        try(Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(PREPARED)) {
            connection.setAutoCommit(false);
            for (FileInfo migrationFile : migrationFiles) {
                String sqlFile = sqlReader.read(migrationFile.getPath());
                statement.execute(sqlFile);
                buildPreparedStatement(migrationFile,preparedStatement);
                preparedStatement.execute();
            }
            connection.commit();
            log.info("Commit transaction");
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                log.error("Rollback transaction", e);
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Failed to rollback transaction", ex);
            }

        }

    }

    public void lockDatabase() {
        log.info("Start locking database");
        while (isLocked()) {
            try {
                log.info("Waiting for unlock");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Failed to lock database(deadlock)", e);
            }
        }
        try (Statement statement = connection.createStatement()) {
            String query = "UPDATE variables SET value = true";
            statement.executeUpdate(query);
            log.info("Database has been locked");
        } catch (SQLException ex) {
            log.error("Failed to lock database", ex);
        }
    }

    public void unlockDatabase() {
        log.info("Start unlocking database");
        try (Statement statement = connection.createStatement()) {
            String query = "UPDATE variables SET value = false";
            statement.executeUpdate(query);
            log.info("Database has been unlocked");
        } catch (SQLException e) {
            log.info("Failed to unlock database", e);
        }
    }
    private boolean isLocked() {
        boolean isLocked = false;
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT value from variables";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            isLocked = resultSet.getBoolean("value");
        } catch (SQLException e) {
            log.error("Failed to check database lock", e);
        }
        return isLocked;
    }
    private void buildPreparedStatement(FileInfo fileInf, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, fileInf.getVersion());
        preparedStatement.setString(2, fileInf.getDescription());
        preparedStatement.setString(3, fileInf.getType());
        preparedStatement.setString(4, "script");
        preparedStatement.setLong(5, 0);
        preparedStatement.setString(6, "installed_by");
        preparedStatement.setLong(7, 0);
        preparedStatement.setBoolean(8, true);
    }
}

package innowise.internship.services;

import innowise.internship.dto.FileInfo;
import innowise.internship.utils.SQLFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class MigrationExecutor {
    private static final String MIGRATION_HISTORY_CREATE = """
          CREATE TABLE IF NOT EXISTS migration_history (
                id SERIAL PRIMARY KEY,
                version VARCHAR(255) NOT NULL,
                description VARCHAR(255) NOT NULL,
                type VARCHAR(255) NOT NULL,
                script TEXT NOT NULL,
                checksum BIGINT NOT NULL,
                installed_by VARCHAR(255) NOT NULL,
                installed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                execution_time_ms BIGINT NOT NULL,
                success BOOLEAN NOT NULL) """;
    private static final String CONCURENCY_FLAG_CREATE = """
          DO $$
          BEGIN
              IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'variables') THEN
                  CREATE TABLE variables (
                      key TEXT PRIMARY KEY,
                      value BOOLEAN
                  );
                  INSERT INTO variables (key, value) VALUES ('concurrency_flag', false);
              END IF;
          END $$;""";
    private static final String PREPARED = "INSERT INTO migration_history(version, description, type, script, checksum, installed_by, execution_time_ms, success) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_VARIABLES_FALSE = "UPDATE variables SET value = false";
    private static final String UPDATE_VARIABLES_TRUE = "UPDATE variables SET value = true";
    private static final String GET_STATE = "SELECT value from variables";

    private Connection connection;
    private final SQLFileUtil sqlFileUtil;

    public void createMigrationTableIfNotExist() {
        log.info("Creating migration table if not exist");
        try(Statement statement = connection.createStatement()) {
            statement.execute(MIGRATION_HISTORY_CREATE);
        } catch (SQLException e) {
            log.error("Failed to create migration table", e);
        }
    }
    public void createConcurrencyTableIfNotExist() {
        log.info("Creating concurrency table if not exist");
        try(Statement statement = connection.createStatement()) {
            statement.execute(CONCURENCY_FLAG_CREATE);
        } catch (SQLException e) {
            log.error("Failed to create concurrency table", e);
        }
    }

    public void executeMigration(List<FileInfo> migrationFiles) {
        log.info("Start executing migrations");
        try(Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(PREPARED)) {
            connection.setAutoCommit(false);
            executeMigrations(statement, preparedStatement, migrationFiles);
            connection.commit();
            connection.setAutoCommit(true);
            log.info("Commit transaction");
        } catch (SQLException e) {
            log.error("Rollback transaction", e);
            rollback();
        }
    }

    public void lockDatabase() {
        log.info("Start locking database");
        sleep();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(UPDATE_VARIABLES_TRUE);
            log.info("Database has been locked");
        } catch (SQLException ex) {
            log.error("Failed to lock database", ex);
        }
    }

    public void unlockDatabase() {
        log.info("Start unlocking database");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(UPDATE_VARIABLES_FALSE);
            log.info("Database has been unlocked");
        } catch (SQLException e) {
            log.info("Failed to unlock database", e);
        }
    }

    private boolean isLocked() {
        boolean isLocked = false;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_STATE);
            resultSet.next();
            isLocked = resultSet.getBoolean("value");
        } catch (SQLException e) {
            log.error("Failed to check database lock", e);
        }
        return isLocked;
    }

    private void executeMigrations(Statement statement,
                                   PreparedStatement preparedStatement,
                                   List<FileInfo> migrationFiles) throws SQLException {
        for (FileInfo migrationFile : migrationFiles) {
            String sqlFile = sqlFileUtil.read(migrationFile.getPath());
            buildPreparedStatement(migrationFile,preparedStatement);
            statement.execute(sqlFile);
            preparedStatement.execute();
        }
    }
    private void buildPreparedStatement(FileInfo fileInf, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, fileInf.getVersion());
        preparedStatement.setString(2, fileInf.getDescription());
        preparedStatement.setString(3, fileInf.getType());
        preparedStatement.setString(4, "script");
        preparedStatement.setLong(5, 0);
        preparedStatement.setString(6, System.getProperty("user.name"));
        preparedStatement.setLong(7, 0);
        preparedStatement.setBoolean(8, true);
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            log.error("Failed to rollback transaction", ex);
        }
    }

    private void sleep() {
        while (isLocked()) {
            try {
                log.info("Waiting for unlock");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Failed to lock database(deadlock)", e);
            }
        }
    }
}

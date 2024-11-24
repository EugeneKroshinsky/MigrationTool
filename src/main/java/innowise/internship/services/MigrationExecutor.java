package innowise.internship.services;

import innowise.internship.db.DatabaseType;
import innowise.internship.db.SqlQueries;
import innowise.internship.dto.FileInfo;
import innowise.internship.utils.SqlFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class MigrationExecutor {
    private Connection connection;
    private final SqlFileUtil sqlFileUtil;
    private final DatabaseType databaseType;

    public void createMigrationTableIfNotExist() {
        log.info("Creating migration table if not exist");
        try(Statement statement = connection.createStatement()) {
            statement.execute(SqlQueries.getMigrationHistoryCreate());
        } catch (SQLException e) {
            log.error("Failed to create migration table", e);
        }
    }
    public void createConcurrencyTableIfNotExist() {
        log.info("Creating concurrency table if not exist");
        try(Statement statement = connection.createStatement()) {
            statement.addBatch(SqlQueries.getConcurrencyFlagCreateTable(databaseType));
            statement.addBatch(SqlQueries.getConcurrencyFlagInsertValue(databaseType));
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("Failed to create concurrency table", e);
        }
    }

    public void executeMigration(List<FileInfo> migrationFiles) {
        log.info("Start executing migrations");
        try(Statement statement = connection.createStatement();
            PreparedStatement preparedStatement
                    = connection.prepareStatement(SqlQueries.getInsertMigrationHistoryQuery())) {
            connection.setAutoCommit(false);
            executeMigrations(statement, preparedStatement, migrationFiles);
            connection.commit();
            connection.setAutoCommit(true);
            log.info("Commit transaction");
        } catch (SQLException e) {
            rollback();
        }
    }

    public void lockDatabase() {
        log.info("Start locking database");
        while (isLocked()) {
            sleep();
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.getUpdateVariablesQuery(true));
            log.info("Database has been locked");
        } catch (SQLException ex) {
            log.error("Failed to lock database", ex);
        }
    }

    public void unlockDatabase() {
        log.info("Start unlocking database");
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQueries.getUpdateVariablesQuery(false));
            log.info("Database has been unlocked");
        } catch (SQLException e) {
            log.info("Failed to unlock database", e);
        }
    }

    private boolean isLocked() {
        boolean isLocked = false;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQueries.getState());
            resultSet.next();
            isLocked = resultSet.getBoolean("isLocked");
        } catch (SQLException e) {
            log.error("Failed to check database lock", e);
        }
        return isLocked;
    }
    private void executeMigrations(Statement statement,
                                   PreparedStatement preparedStatement,
                                   List<FileInfo> migrationFiles) throws SQLException{
        for (FileInfo migrationFile : migrationFiles) {
            log.info("Executing migration: {}", migrationFile.getFilename());
            List<String> sqlQueries = sqlFileUtil.getSeparateQueries(migrationFile.getPath());
            buildPreparedStatement(migrationFile,preparedStatement);
            for (String sqlQuery : sqlQueries) {
                statement.addBatch(sqlQuery);
            }
            preparedStatement.execute();
        }
        statement.executeBatch();
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
            log.info("Rollback transaction");
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            log.error("Failed to rollback transaction", ex);
        }
    }
    private void sleep() {
        try {
            log.info("Waiting for unlock");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Failed to lock database(deadlock)", e);
        }
    }
}

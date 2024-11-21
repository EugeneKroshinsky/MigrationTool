package innowise.internship.services;

import innowise.internship.utils.PropertiesUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class MigrationExecutor {
    private final Connection connection = ConnectionManager.getConnection();
    private final SQLReader sqlReader = new SQLReader();
    private final Properties properties = PropertiesUtils.getProperties("application.properties");

    public void createMigrationTableIfNotExist() {
        Path path = Paths.get(properties.getProperty("concurrency.script.path"));
        List<String> sqlFile = sqlReader.readSQLFile(path);
        try(Statement statement = connection.createStatement()) {
            StringBuilder result = new StringBuilder();
            sqlFile.forEach(line ->  result.append(line + "\n"));
            statement.execute(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createConcurrencyTableIfNotExist() {
        Path path = Paths.get(properties.getProperty("migration.script.path"));
        List<String> sqlFile = sqlReader.readSQLFile(path);
        try(Statement statement = connection.createStatement()) {
            StringBuilder result = new StringBuilder();
            sqlFile.forEach(line ->  result.append(line + "\n"));
            statement.execute(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public void executeMigration(List<FileInfo> migrationFiles) {
//        String PREPARED = "INSERT INTO migration_history " +
//                "(version, description, type, script, checksum, installed_by, execution_time_ms, success) " +
//                "VALUES (?,?,?,?,?,?,?,?)";
//        try(Statement statement = connection.createStatement();
//            PreparedStatement preparedStatement = connection.prepareStatement(PREPARED)) {
//            for (FileInfo migrationFile : migrationFiles) {
//                StringBuilder result = new StringBuilder();
//                List<String> sqlFile = sqlReader.readSQLFile(migrationFile.getPath());
//                sqlFile.forEach(line -> result.append(line + "\n"));
//                preparedStatement.setString(1, migrationFile.getVersion());
//                preparedStatement.setString(2, migrationFile.getDescription());
//                preparedStatement.setString(3, migrationFile.getType());
//                preparedStatement.setString(4, "script");
//                preparedStatement.setLong(5, 0);
//                preparedStatement.setString(6, "installed_by");
//                preparedStatement.setLong(7, 0);
//                preparedStatement.setBoolean(8, true);
//                statement.execute(result.toString());
//                preparedStatement.execute();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void lockDatabase() {
        while (isLocked()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try (Statement statement = connection.createStatement()) {
            String query = "UPDATE variables SET value = true";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void unlockDatabase() {
        try (Statement statement = connection.createStatement()) {
            String query = "UPDATE variables SET value = false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return isLocked;
    }
}

package innowise.internship.services;

import innowise.internship.dto.FileInfo;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MigrationExecutor {
    private Connection connection = ConnectionManager.getConnection();
    private SQLReader sqlReader = new SQLReader();

    public void createMigrationTableIfNotExist() {
        List<String> sqlFile
                = sqlReader.readSQLFile(Paths.get("C:\\github\\MigrationTool\\src\\main\\resources\\db\\migration\\default_scripts\\migration_history.sql"));
        try(Statement statement = connection.createStatement()) {
            StringBuilder result = new StringBuilder();
            sqlFile.forEach(line ->  result.append(line + "\n"));
            statement.execute(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createConcurrencyTableIfNotExist() {
        List<String> sqlFile
                = sqlReader.readSQLFile(Paths.get("C:\\github\\MigrationTool\\src\\main\\resources\\db\\migration\\default_scripts\\concurrency_flag.sql"));
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
}

package innowise.internship.services;

import java.nio.file.Paths;
import java.sql.Connection;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package innowise.internship.services.status;

import innowise.internship.db.SqlQueries;
import innowise.internship.dto.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
@AllArgsConstructor
public class StatusExecutor {
    private Connection connection;

    public Status getStatus() {
        log.info("Get status");
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlQueries.getLastRow());
            resultSet.next();
            String version = resultSet.getString("version");
            Date date = resultSet.getDate("installed_on");
            String description = resultSet.getString("description");
            String login = resultSet.getString("installed_by");
            Status status = new Status(version, description, login, date);
            return status;
        } catch (SQLException e) {
            log.error("Failed to get status", e);
            throw new RuntimeException();
        }
    }
}

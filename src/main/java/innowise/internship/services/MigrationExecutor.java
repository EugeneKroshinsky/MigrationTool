package innowise.internship.services;

import java.sql.Connection;

public class MigrationExecutor {
    private Connection connection = ConnectionManager.getConnection();
    public void createTableIfNotExist() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

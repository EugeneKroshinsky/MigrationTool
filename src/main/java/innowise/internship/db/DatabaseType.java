package innowise.internship.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public enum DatabaseType {
    MYSQL("mysql"),
    POSTGRESQL("postgresql"),
    UNKNOWN("unknown");

    private final String productName;

    DatabaseType(String productName) {
        this.productName = productName;
    }

    public static DatabaseType fromConnection(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String productName = metaData.getDatabaseProductName().toLowerCase();

            return switch (productName) {
                case "mysql" -> DatabaseType.MYSQL;
                case "postgresql" -> DatabaseType.POSTGRESQL;
                default -> DatabaseType.UNKNOWN;
            };
        } catch (SQLException e) {
            throw new RuntimeException("Failed to determine database type", e);
        }
    }
}

package innowise.internship.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public enum DatabaseType {
    MYSQL("mysql"),
    POSTGRESQL("postgresql"),
    H2("h2"),
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
                case "h2" -> DatabaseType.H2;
                default -> DatabaseType.UNKNOWN;
            };
        } catch (SQLException e) {
            throw new RuntimeException("Failed to determine database type", e);
        }
    }
}

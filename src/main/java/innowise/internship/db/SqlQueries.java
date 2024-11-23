package innowise.internship.db;

public final class SqlQueries {
    private SqlQueries() {}

    public static String getMigrationHistoryCreate() {
            return """
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
                success BOOLEAN NOT NULL)
            """;
    }

    public static String getInsertMigrationHistoryQuery() {
        return """
            INSERT INTO migration_history(
                version, description, type, script, 
                checksum, installed_by, execution_time_ms, success
            ) VALUES (?,?,?,?,?,?,?,?)
            """;
    }

    public static String getUpdateVariablesQuery(boolean value) {
        return value ? "UPDATE concurrency SET isLocked = true"
                : "UPDATE concurrency SET isLocked = false";
    }

    public static String getConcurrencyFlagCreateTable(DatabaseType databaseType) {
        return """
               CREATE TABLE IF NOT EXISTS concurrency (
                    concurrency_key VARCHAR(30) PRIMARY KEY,
                    isLocked BOOLEAN
               );
               """;
    }
    public static String getConcurrencyFlagInsertValue(DatabaseType databaseType) {
        if (databaseType.equals(DatabaseType.POSTGRESQL)
                || databaseType.equals(DatabaseType.H2)) {
            return """
                   INSERT INTO concurrency (concurrency_key, isLocked)
                   VALUES ('concurrency_flag', FALSE)
                   ON CONFLICT (concurrency_key) DO NOTHING;
                   """;
        } else if (databaseType.equals(DatabaseType.MYSQL)) {
            return """
                    INSERT IGNORE INTO concurrency (concurrency_key, isLocked)
                    VALUES ('concurrency_flag', FALSE);
                    """;
        } else {
            throw new RuntimeException("Database type haven't define");
        }
    }
    public static String getState() {
        return "SELECT isLocked FROM concurrency";
    }
}
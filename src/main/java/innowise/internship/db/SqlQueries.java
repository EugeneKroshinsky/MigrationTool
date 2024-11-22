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
        return String.format("UPDATE variables SET value = %s", value);
    }

    public static String getConcurrencyFlagCreate(DatabaseType databaseType) {
        if (databaseType.equals(DatabaseType.POSTGRESQL)) {
            return """
                    DO $$
                    BEGIN
                        IF NOT EXISTS (SELECT 1 FROM information_schema.tables 
                            WHERE table_name = 'variables') THEN
                            CREATE TABLE variables (
                                key TEXT PRIMARY KEY,
                                value BOOLEAN
                            );
                            INSERT INTO variables (key, value) VALUES ('concurrency_flag', false);
                        END IF;
                    END $$;
                    """;
        } else if (databaseType.equals(DatabaseType.MYSQL)) {
            return """
                        CREATE TABLE IF NOT EXISTS `variables` (
                            `key` VARCHAR(255) PRIMARY KEY,
                            `value` BOOLEAN
                        );
                        INSERT IGNORE INTO variables (`key`, `value`)
                        VALUES ('concurrency_flag', FALSE);
                    """;
        } else {
            throw new RuntimeException("Database type haven't define");
        }
    }
    public static String getState() {
        return "SELECT value FROM variables";
    }
}
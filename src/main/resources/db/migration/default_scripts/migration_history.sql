CREATE TABLE IF NOT EXISTS migration_history (
    id SERIAL PRIMARY KEY,
    version VARCHAR(30) NOT NULL,
    description VARCHAR(30) NOT NULL,
    type VARCHAR(10) NOT NULL,
    script VARCHAR(30) NOT NULL,
    checksum BIGINT NOT NULL,
    installed_by TEXT NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT NOW(),
    execution_time_ms INT,
    success BOOLEAN NOT NULL
);
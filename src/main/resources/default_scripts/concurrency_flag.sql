DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'variables') THEN
        CREATE TABLE variables (
            key TEXT PRIMARY KEY,
            value BOOLEAN
        );
        INSERT INTO variables (key, value) VALUES ('concurrency_flag', false);
    END IF;
END $$;

package innowise.internship.services.rollback;

import innowise.internship.db.DatabaseType;
import innowise.internship.utils.SqlFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
@AllArgsConstructor
public class RollbackExecutor {
    private Connection connection;
    private final SqlFileUtil sqlFileUtil;
    private final DatabaseType databaseType;

}

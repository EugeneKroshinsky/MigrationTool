//package innowise.internship.controller;
//
//import innowise.internship.db.ConnectionManager;
//import innowise.internship.db.DatabaseType;
//import innowise.internship.services.migrations.MigrationExecutor;
//import innowise.internship.services.rollback.RollbackExecutor;
//import innowise.internship.services.rollback.RollbackFileFinder;
//import innowise.internship.services.rollback.RollbackManger;
//import innowise.internship.utils.PropertiesUtils;
//import innowise.internship.utils.SqlFileUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.Connection;
//import java.util.List;
//import java.util.Properties;
//
//@Slf4j
//public class RollbackController {
//    private static final Properties properties;
//    private static final SqlFileUtil sqlFileUtil;
//    private static final RollbackManger rollbackManager;
//    private static final RollbackExecutor rollbackExecutor;
//    private static final RollbackFileFinder rollbackFileFinder;
//    private static final DatabaseType databaseType;
//    private static Connection connection;
//
//    static {
//        log.info("MigrationTool start initialization");
//        properties = PropertiesUtils.getProperties("application.properties");
//        sqlFileUtil = new SqlFileUtil();
//        connection = ConnectionManager.getConnection();
//        databaseType = DatabaseType.fromConnection(connection);
//        rollbackFileFinder = new RollbackFileFinder(connection);
//        rollbackManager = new RollbackManger();
//        rollbackExecutor = new RollbackExecutor(connection, sqlFileUtil, databaseType);
//        log.info("MigrationTool finish initialization");
//    }
//
//    public static void rollback (){
//        log.info("MigrationTool start run");
//        List<String> migrationFiles = rollbackFileFinder.getAllExecutedVersions();
//        List<String> migrationFilesToExecute = rollbackManager.getNewMigrations(migrationFiles);
//        executeMigrations(migrationFilesToExecute);
//        log.info("MigrationTool finish run");
//    }
//
//    private static void executeMigrations(List<String> migrationFiles) {
//        if (!migrationFiles.isEmpty()) {
//            rollbackExecutor.executeMigration(migrationFiles);
//        } else {
//            log.info("No new migrations to execute");
//        }
//    }
//}

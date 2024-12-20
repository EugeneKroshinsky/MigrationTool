package innowise.internship.controller;


import innowise.internship.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class MigrationTool {
    private static final Properties properties = PropertiesUtils.getProperties("application.properties");
    public static void run() {
        log.info("MigrationTool starts");
        switch (properties.getProperty("command")) {
            case "migrate":
                MigrationController.run();
                break;
            case "status":
                StatusController.getStatus();
                break;
            case "rollback":
                RollbackController.rollback();
                break;
            default:
                log.error("Unknown command");
                throw new RuntimeException("Unknown command");
        }
        log.info("MigrationTool has been finished");
    }
}

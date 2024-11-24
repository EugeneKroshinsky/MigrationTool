package innowise.internship.controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MigrationTool {
    private static final MigrationController migrationController = new MigrationController();;
    public static void run(String[] args) {
        for (String arg : args) {
            switch (arg.toLowerCase()) {
                case "migrate":
                    migrationController.run();
                    break;
                default:
                    log.error("Unknown command");
                    throw new RuntimeException("Unknown command");
            }
        }
        log.info("No more commands");
    }
}

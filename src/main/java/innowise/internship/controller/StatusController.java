package innowise.internship.controller;

import innowise.internship.db.ConnectionManager;
import innowise.internship.dto.Status;
import innowise.internship.services.status.StatusExecutor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

@Slf4j
public class StatusController {
    private static Connection connection;
    private static final StatusExecutor statusExecutor;

    static {
        connection = ConnectionManager.getConnection();
        statusExecutor = new StatusExecutor(connection);
    }
    public static void getStatus() {
        log.info("StatusController starts");
        Status status = statusExecutor.getStatus();
        log.info("Status: {}", status);
    }
}

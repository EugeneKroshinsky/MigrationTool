package innowise.internship.services;

import innowise.internship.utils.PropertiesUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    @Getter private static final Connection connection;
    static {
        Properties properties = PropertiesUtils.getProperties(PROPERTIES_FILE_NAME);
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            //log.error("Failed to create connection", e);
            throw new RuntimeException("Failed to create connection", e);
        }
    }
}
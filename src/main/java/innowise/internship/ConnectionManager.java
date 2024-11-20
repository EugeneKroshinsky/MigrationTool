package innowise.internship;

import innowise.internship.utils.PropertiesUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    public Connection getConnection(String propertiesPath) throws SQLException {
        Properties properties = PropertiesUtils.getProperties(propertiesPath);
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        return DriverManager.getConnection(url, username, password);
    }
}


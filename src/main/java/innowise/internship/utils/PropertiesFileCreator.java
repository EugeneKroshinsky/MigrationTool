package innowise.internship.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class PropertiesFileCreator {
    private static final Properties properties = PropertiesUtils.getProperties("application.properties");
    public static void create(String[] args) {
        if (args.length < 5) {
            log.error("Not enough arguments");
            return;
        }
        properties.setProperty("filepath", args[0]);
        properties.setProperty("command", args[1]);
        properties.setProperty("db.username", args[2]);
        properties.setProperty("db.password", args[3]);
        properties.setProperty("db.url", args[4]);
    }
}

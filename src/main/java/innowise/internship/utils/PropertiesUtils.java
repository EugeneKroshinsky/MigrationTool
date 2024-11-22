package innowise.internship.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesUtils {
    private static final Properties properties;
    static {
        properties = new Properties();
    }
    public static Properties getProperties(String propertiesFileName) {
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream == null) {
                throw new RuntimeException("Failed to load properties file");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Failed to load properties file", e);
            throw new RuntimeException("Failed to load properties file", e);
        }
        return properties;
    }
}
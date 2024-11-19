package innowise.internship.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    private final Properties properties;

    public PropertiesUtils(String propertiesFile) {
        properties = new Properties();
        loadProperties(propertiesFile);
    }

    private void loadProperties(String propertiesFile) {
        try (InputStream stream =
                     getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) throws IllegalArgumentException{
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property " + key + " not found");
        }
        return value;
    }
}
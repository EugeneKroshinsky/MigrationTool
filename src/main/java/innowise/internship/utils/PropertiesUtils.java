package innowise.internship.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {
    public static Properties getProperties(String propertiesFileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
package innowise.internship.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilsTest {

    @Test
    public void testVariable() {
        Properties properties = PropertiesUtils.getProperties("testApplication.properties");
        assertEquals("testValue", properties.getProperty("testVariable"));
    }
    @Test
    public void testNotExistVariable() {
        assertThrows(NullPointerException.class,
                () -> PropertiesUtils.getProperties("notExistVariable"));
    }
}
package innowise.internship.utils;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilsTest {

    @Test
    public void testVariable() {
        Properties properties = PropertiesUtils.getProperties("testVariable");
        assertEquals("testValue", properties.getProperty("testVariable"));
    }
    @Test
    public void testNotExistVariable() {
        assertThrows(IllegalArgumentException.class
                , () -> PropertiesUtils.getProperties("notExistVariable"));
    }
}
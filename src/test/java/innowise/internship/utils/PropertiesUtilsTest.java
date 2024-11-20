package innowise.internship.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilsTest {
    private final PropertiesUtils propertiesUtils
            = new PropertiesUtils("testApplication.properties");
    @Test
    public void testVariable() {
        String testValue = propertiesUtils.getProperty("testVariable");
        assertEquals("testValue", testValue);
    }
    @Test
    public void testNotExistVariable() {
        assertThrows(IllegalArgumentException.class, () -> propertiesUtils.getProperty("notExistVariable"));
    }
}
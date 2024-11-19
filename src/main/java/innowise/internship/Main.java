package innowise.internship;

import innowise.internship.utils.PropertiesUtils;

public class Main {
    public static void main(String[] args) {
        PropertiesUtils propertiesUtils = new PropertiesUtils("application.properties");
        System.out.println(propertiesUtils.getProperty("USERNAME"));
    }
}
package org.complexAQA.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Properties {

    private static java.util.Properties props = new java.util.Properties();
    private static final String HOST;
    private static final String USER_URL;


    static {

        try (InputStream input = Properties.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new FileNotFoundException("Sorry, unable to find application.properties");
            }
            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }

        HOST = getPropertyValue("host");
        USER_URL = getPropertyValue("userURL");
    }


    private static String getPropertyValue(String key) {
        String value = props.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value;
    }

    public static String getHost() {
        return HOST;
    }

    public static String getUserUrl() {
        return USER_URL;
    }

}

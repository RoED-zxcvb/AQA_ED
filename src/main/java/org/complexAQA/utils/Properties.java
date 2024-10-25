package org.complexAQA.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Properties {

    private static final java.util.Properties props = new java.util.Properties();


    static {

        try (InputStream input = Properties.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new FileNotFoundException("Sorry, unable to find application.properties");
            }
            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }

    }


    public static String getPropertyValue(String key) {
        String value = props.getProperty(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value;
    }

}

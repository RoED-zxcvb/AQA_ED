package org.someProject.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Configs {
    static Properties props = new Properties();

    static {

        try (InputStream input = Configs.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new FileNotFoundException("Sorry, unable to find config.properties");
            }
            props.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


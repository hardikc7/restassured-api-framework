package com.sdet.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties");
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException(
                "config.properties not found: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}

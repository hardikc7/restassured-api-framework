package com.sdet.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();

    static {
        try {
            // ✅ Read env from -Denv=dev, default to dev
            String env = System.getProperty("env", "dev");
            System.out.println("Running on environment: " + env);

            String fileName = env + ".properties";

            InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(fileName);

            if (input == null) {
                throw new RuntimeException(
                    fileName + " not found in resources");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                "Failed to load config: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
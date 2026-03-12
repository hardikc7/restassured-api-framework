package com.sdet.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataReader {

    public static String getRequestBody(String fileName) {
        try {
            return new String(Files.readAllBytes(
                Paths.get("src/test/resources/testdata/" 
                    + fileName)));
        } catch (IOException e) {
            throw new RuntimeException(
                "Test data file not found: " + fileName);
        }
    }
}
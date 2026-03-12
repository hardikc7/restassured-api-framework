package com.sdet.base;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setUp() {
        // ✅ Set base URL once for entire suite
        RestAssured.baseURI = ConfigReader.get("baseUrl");

        // ✅ Log every request and response automatically
        RestAssured.filters(
            new RequestLoggingFilter(),
            new ResponseLoggingFilter()
        );
    }
}
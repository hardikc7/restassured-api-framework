package com.sdet.api;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class DeleteUserTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("baseUrl");
    }

    // ✅ TEST 1 — Delete existing user
    @Test
    public void testDeleteUser() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .delete("/users/1");

        System.out.println("=== TEST 1: Delete User ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body: " + 
            response.body().asString());
        System.out.println("==========================");

        response.then().statusCode(200);
    }

    // ✅ TEST 2 — Verify response body is empty after delete
    @Test
    public void testDeleteResponseBodyEmpty() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .delete("/users/1");

        System.out.println("=== TEST 2: Delete Body Empty ===");
        System.out.println("Status Code  : " + response.statusCode());
        System.out.println("Body length  : " + 
            response.body().asString().length());
        System.out.println("=================================");

        response.then().statusCode(200);

        // ✅ Body should be empty after delete
        Assert.assertTrue(
            response.body().asString().length() <= 2,
            "Body should be empty after delete");
    }

    // ✅ TEST 3 — Delete response time
    @Test
    public void testDeleteResponseTime() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .delete("/users/1");

        long responseTime = response.getTime();

        System.out.println("=== TEST 3: Delete Response Time ===");
        System.out.println("Response Time: " + responseTime + "ms");
        System.out.println("====================================");

        response.then().statusCode(200);
        Assert.assertTrue(responseTime < 3000,
            "Delete too slow: " + responseTime + "ms");
    }
}
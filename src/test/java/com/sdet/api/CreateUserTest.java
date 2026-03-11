package com.sdet.api;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("baseUrl");
    }

    // ✅ TEST 1 — Create user — verify 201 + fields match
    @Test
    public void testCreateUser() {
        String requestBody = """
                {
                    "name": "Hardik Shah",
                    "username": "hardik.shah",
                    "email": "hardik@sdet.com",
                    "phone": "1234567890",
                    "website": "sdet.com"
                }
                """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users");

        System.out.println("=== TEST 1: Create User ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body:");
        response.prettyPrint();
        System.out.println("==========================");

        response.then()
            .statusCode(201)
            .body("name",     equalTo("Hardik Shah"))
            .body("email",    equalTo("hardik@sdet.com"))
            .body("id",       notNullValue());
    }

    // ✅ TEST 2 — Capture and use created user ID
    @Test
    public void testCaptureCreatedUserId() {
        String requestBody = """
                {
                    "name": "Test User",
                    "username": "test.user",
                    "email": "test@sdet.com"
                }
                """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users");

        int userId = response.jsonPath().getInt("id");

        System.out.println("=== TEST 2: Capture User ID ===");
        System.out.println("Status Code    : " + response.statusCode());
        System.out.println("Created User ID: " + userId);
        System.out.println("==============================");

        response.then().statusCode(201);
        Assert.assertTrue(userId > 0,
            "User ID should be positive: " + userId);
    }

    // ✅ TEST 3 — Verify response headers
    @Test
    public void testResponseHeaders() {
        String requestBody = """
                {
                    "name": "Header Test",
                    "email": "header@sdet.com"
                }
                """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post("/users");

        System.out.println("=== TEST 3: Response Headers ===");
        System.out.println("Content-Type: " + 
            response.header("Content-Type"));
        System.out.println("================================");

        response.then()
            .statusCode(201)
            .header("Content-Type", containsString("application/json"));
    }
}
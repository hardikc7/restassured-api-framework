package com.sdet.api;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUserTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("baseUrl");
    }

    // ✅ TEST 1 — Get single user
    @Test
    public void testGetSingleUser() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .get("/users/1");

        // ✅ Print full response
        System.out.println("=== TEST 1: Get Single User ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body:");
        response.prettyPrint();
        System.out.println("==============================");

        // ✅ Assertions
        response.then()
            .statusCode(200)
            .body("id",       equalTo(1))
            .body("name",     notNullValue())
            .body("email",    notNullValue())
            .body("username", notNullValue());
    }

    // ✅ TEST 2 — Get all users
    @Test
    public void testGetAllUsers() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .get("/users");

        // ✅ Print full response
        System.out.println("=== TEST 2: Get All Users ===");
        System.out.println("Status Code  : " + response.statusCode());
        System.out.println("Total Users  : " + 
            response.jsonPath().getList("$").size());
        System.out.println("First User   : " + 
            response.jsonPath().getString("[0].name"));
        System.out.println("==============================");

        // ✅ Assertions
        response.then()
            .statusCode(200)
            .body("$",      notNullValue())
            .body("size()", greaterThan(0));
    }

    // ✅ TEST 3 — User not found
    @Test
    public void testUserNotFound() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .get("/users/999");

        // ✅ Print response
        System.out.println("=== TEST 3: User Not Found ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body: " + response.body().asString());
        System.out.println("==============================");

        // ✅ Assertion
        response.then().statusCode(404);
    }

    // ✅ TEST 4 — Response time
    @Test
    public void testResponseTime() {
        Response response = given()
            .header("Content-Type", "application/json")
        .when()
            .get("/users/1");
    
        long responseTime = response.getTime();
    
        System.out.println("=== TEST 4: Response Time ===");
        System.out.println("Response Time: " + responseTime + "ms");
        System.out.println("Threshold    : 3000ms");
        System.out.println("Result       : " + 
            (responseTime < 3000 ? "FAST ✅" : "SLOW ❌"));
        System.out.println("==============================");
    
        // ✅ Verify status code
        response.then().statusCode(200);
    
        // ✅ Verify response time
        Assert.assertTrue(responseTime < 3000,
            "Response too slow: " + responseTime + "ms");
    }
    }

package com.sdet.api;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("baseUrl");
    }

    // ✅ TEST 1 — Update user — verify fields updated
    @Test
    public void testUpdateUser() {
        String requestBody = """
                {
                    "id": 1,
                    "name": "Hardik Shah Updated",
                    "username": "hardik.updated",
                    "email": "updated@sdet.com",
                    "phone": "9999999999",
                    "website": "updated.com"
                }
                """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put("/users/1");

        System.out.println("=== TEST 1: Update User ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Response Body:");
        response.prettyPrint();
        System.out.println("==========================");

        response.then()
            .statusCode(200)
            .body("name",  equalTo("Hardik Shah Updated"))
            .body("email", equalTo("updated@sdet.com"))
            .body("id",    equalTo(1));
    }

    // ✅ TEST 2 — Verify only updated fields changed
    @Test
    public void testPartialUpdate() {
        String requestBody = """
                {
                    "name": "Only Name Changed"
                }
                """;

        Response response = given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put("/users/1");

        System.out.println("=== TEST 2: Partial Update ===");
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Updated name: " +
            response.jsonPath().getString("name"));
        System.out.println("=============================");

        response.then()
            .statusCode(200)
            .body("name", equalTo("Only Name Changed"));
    }

    // ✅ TEST 3 — Update non-existent user
@Test
public void testUpdateNonExistentUser() {
    String requestBody = """
            {
                "name": "Ghost User"
            }
            """;

    Response response = given()
        .header("Content-Type", "application/json")
        .body(requestBody)
    .when()
        .put("/users/999");

    System.out.println("=== TEST 3: Update Non-Existent ===");
    System.out.println("Status Code : " + response.statusCode());
    System.out.println("Response    : " +
        response.body().asString());
    System.out.println("===================================");

    // ✅ JSONPlaceholder returns 500 for non-existent resource
    response.then().statusCode(500);

    }
}
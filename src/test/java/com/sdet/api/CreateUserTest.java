package com.sdet.api;

import com.sdet.base.BaseTest;
import com.sdet.clients.UserApiClient;
import com.sdet.utils.TestDataReader;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends BaseTest {

    private UserApiClient userClient;
    private String requestBody;

    @BeforeClass
    public void init() {
        userClient   = new UserApiClient();
        requestBody  = TestDataReader.getRequestBody(
            "createUser.json");
    }

    @Test
    public void testCreateUser() {
        Response response = userClient.createUser(requestBody);

        response.then()
            .statusCode(201)
            .body("name",  equalTo("Hardik Shah"))
            .body("email", equalTo("hardik@sdet.com"))
            .body("id",    notNullValue());

        int userId = response.jsonPath().getInt("id");
        System.out.println("Created ID: " + userId);
        Assert.assertTrue(userId > 0,
            "Created user ID should be positive");
    }

    @Test
    public void testCaptureCreatedUserId() {
        Response response = userClient.createUser(requestBody);

        int userId = response.jsonPath().getInt("id");
        System.out.println("Created User ID: " + userId);

        response.then().statusCode(201);
        Assert.assertTrue(userId > 0,
            "User ID should be positive: " + userId);
    }

    @Test
    public void testResponseHeaders() {
        Response response = userClient.createUser(requestBody);

        response.then()
            .statusCode(201)
            .header("Content-Type",
                containsString("application/json"));

        Assert.assertEquals(response.statusCode(), 201,
            "Expected 201 Created");
    }
}
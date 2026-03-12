package com.sdet.api;

import com.sdet.base.BaseTest;
import com.sdet.clients.UserApiClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class GetUserTest extends BaseTest {

    private UserApiClient userClient;

    @BeforeClass
    public void init() {
        userClient = new UserApiClient();
    }

    @Test
    public void testGetSingleUser() {
        Response response = userClient.getUser(1);

        // ✅ RestAssured assertion
        response.then()
            .statusCode(200)
            .body("id",    equalTo(1))
            .body("name",  notNullValue())
            .body("email", notNullValue());

        // ✅ TestNG assertion with custom message
        Assert.assertEquals(response.jsonPath().getInt("id"), 1,
            "User ID should be 1");
        Assert.assertNotNull(
            response.jsonPath().getString("name"),
            "Name should not be null");
    }

    @Test
    public void testGetAllUsers() {
        Response response = userClient.getAllUsers();

        response.then()
            .statusCode(200)
            .body("$",      notNullValue())
            .body("size()", greaterThan(0));

        int totalUsers = response.jsonPath().getList("$").size();
        System.out.println("Total users: " + totalUsers);
        Assert.assertTrue(totalUsers > 0,
            "User list should not be empty");
    }

    @Test
    public void testUserNotFound() {
        Response response = userClient.getUser(999);

        response.then().statusCode(404);
        Assert.assertEquals(response.statusCode(), 404,
            "Expected 404 for non-existent user");
    }

    @Test
    public void testResponseTime() {
        Response response = userClient.getUser(1);
        long responseTime = response.getTime();

        System.out.println("Response time: " + responseTime + "ms");

        response.then().statusCode(200);
        Assert.assertTrue(responseTime < 3000,
            "Too slow: " + responseTime + "ms");
    }
}
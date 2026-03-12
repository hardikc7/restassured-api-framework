package com.sdet.api;

import com.sdet.base.BaseTest;
import com.sdet.clients.UserApiClient;
import com.sdet.utils.TestDataReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UpdateUserTest extends BaseTest {

    private UserApiClient userClient;
    private String requestBody;

    @BeforeClass
    public void init() {
        userClient  = new UserApiClient();
        requestBody = TestDataReader.getRequestBody(
            "updateUser.json");
    }

    @Test
    public void testUpdateUser() {
        Response response = userClient.updateUser(1, requestBody);

        response.then()
            .statusCode(200)
            .body("name",  equalTo("Hardik Shah Updated"))
            .body("email", equalTo("updated@sdet.com"));

        Assert.assertEquals(
            response.jsonPath().getString("name"),
            "Hardik Shah Updated",
            "Name should be updated");
    }

    @Test
    public void testPartialUpdate() {
        String partialBody = "{\"name\": \"Only Name Changed\"}";

        Response response = userClient.updateUser(1, partialBody);

        response.then()
            .statusCode(200)
            .body("name", equalTo("Only Name Changed"));

        Assert.assertEquals(
            response.jsonPath().getString("name"),
            "Only Name Changed",
            "Name should be updated");
    }

    @Test
    public void testUpdateNonExistentUser() {
        Response response = userClient.updateUser(999, requestBody);

        response.then().statusCode(500);
        Assert.assertEquals(response.statusCode(), 500,
            "Expected 500 for non-existent user");
    }
}
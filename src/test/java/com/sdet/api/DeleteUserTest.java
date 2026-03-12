package com.sdet.api;

import com.sdet.base.BaseTest;
import com.sdet.clients.UserApiClient;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest {

    private UserApiClient userClient;

    @BeforeClass
    public void init() {
        userClient = new UserApiClient();
    }

    @Test
    public void testDeleteUser() {
        Response response = userClient.deleteUser(1);

        response.then().statusCode(200);
        Assert.assertEquals(response.statusCode(), 200,
            "Expected 200 for delete");
    }

    @Test
    public void testDeleteResponseBodyEmpty() {
        Response response = userClient.deleteUser(1);

        response.then().statusCode(200);
        Assert.assertTrue(
            response.body().asString().length() <= 2,
            "Body should be empty after delete");
    }

    @Test
    public void testDeleteResponseTime() {
        Response response = userClient.deleteUser(1);
        long responseTime = response.getTime();

        System.out.println("Delete time: " + responseTime + "ms");

        response.then().statusCode(200);
        Assert.assertTrue(responseTime < 3000,
            "Too slow: " + responseTime + "ms");
    }
}

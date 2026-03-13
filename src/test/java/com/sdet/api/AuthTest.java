package com.sdet.api;

import com.sdet.clients.AuthApiClient;
import com.sdet.utils.TestDataReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// ✅ Does NOT extend BaseTest
// Self-contained — uses reqres.in only
public class AuthTest {

        private AuthApiClient authClient;
        private String token;

        @BeforeClass
        public void setUp() {
                authClient = new AuthApiClient();

                // ✅ Login once — store token for all tests
                String requestBody = TestDataReader
                                .getRequestBody("authData.json");
                token = authClient.login(requestBody);

                Assert.assertNotNull(token,
                                "Token should not be null after login");
        }

        // ✅ TEST 1 Just assert the token we already have from @BeforeClass
        @Test
        public void testLoginReturnsToken() {
                Assert.assertNotNull(token,
                                "Token should not be null");
                Assert.assertTrue(token.length() > 0,
                                "Token should not be empty");

                System.out.println("Token received: " + token);
        }

        // ✅ TEST 2 — Authenticated GET request works
        @Test
        public void testAuthenticatedGetUser() {
                Response response = authClient.getUser(2, token);
                response.then().statusCode(200);
                // ✅ dummyjson has no "data" wrapper — id is at root
                Assert.assertEquals(
                                response.jsonPath().getInt("id"), 2,
                                "User ID should be 2");
                // ✅ dummyjson uses "firstName" not "data.first_name"
                Assert.assertNotNull(
                                response.jsonPath().getString("firstName"),
                                "First name should not be null");
                System.out.println("Authenticated user: "
                                + response.jsonPath().getString("firstName"));
        }

        // ✅ TEST 3 — Request without token returns 401
        @Test
        public void testRequestWithoutTokenFails() {
                Response response = authClient.loginWithoutCredentials();

                response.then().statusCode(400);
                Assert.assertEquals(response.statusCode(), 400,
                                "Should fail without credentials");
        }
}
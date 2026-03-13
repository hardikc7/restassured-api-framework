package com.sdet.clients;

import com.sdet.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class AuthApiClient {

    private static final String BASE_URL  =
        ConfigReader.get("authBaseUrl");
    private static final String LOGIN_URL =
        ConfigReader.get("loginEndpoint");

    // // ✅ Temporarily clears global spec so reqres.in is used
    // private Response executeClean(RequestSpecification spec) {
    //     RequestSpecification saved = RestAssured.requestSpecification;
    //     RestAssured.requestSpecification = null;
    //     try {
    //         return given(spec).when().post(LOGIN_URL);
    //     } finally {
    //         RestAssured.requestSpecification = saved;
    //     }
    // }

    public String login(String requestBody) {
        RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .addHeader("Content-Type", "application/json")
            .setBody(requestBody)
            .build();

        RequestSpecification saved = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;

        Response response;
        try {
            response = given(spec).when().post(LOGIN_URL);
        } finally {
            RestAssured.requestSpecification = saved;
        }
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token: "  + token);
        return token;
    }

    public Response getUser(int userId, String token) {
        RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer " + token)
            .build();
    
        RequestSpecification saved = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;
    
        try {
            return given(spec).when().get("/users/" + userId);  // ← removed /api
        } finally {
            RestAssured.requestSpecification = saved;
        }
    }

    public Response loginWithoutCredentials() {
        RequestSpecification spec = new RequestSpecBuilder()
            .setBaseUri(BASE_URL)
            .addHeader("Content-Type", "application/json")
            .build();

        RequestSpecification saved = RestAssured.requestSpecification;
        RestAssured.requestSpecification = null;

        try {
            return given(spec).when().post(LOGIN_URL);
        } finally {
            RestAssured.requestSpecification = saved;
        }
    }
}

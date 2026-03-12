package com.sdet.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserApiClient {

    private static final String USERS_ENDPOINT = "/users";

    public Response getUser(int userId) {
        return given()
            .header("Content-Type", "application/json")
        .when()
            .get(USERS_ENDPOINT + "/" + userId);
    }

    public Response getAllUsers() {
        return given()
            .header("Content-Type", "application/json")
        .when()
            .get(USERS_ENDPOINT);
    }

    // ✅ Accepts String body from JSON file
    public Response createUser(String requestBody) {
        return given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post(USERS_ENDPOINT);
    }

    public Response updateUser(int userId, 
                               String requestBody) {
        return given()
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put(USERS_ENDPOINT + "/" + userId);
    }

    public Response deleteUser(int userId) {
        return given()
            .header("Content-Type", "application/json")
        .when()
            .delete(USERS_ENDPOINT + "/" + userId);
    }
}
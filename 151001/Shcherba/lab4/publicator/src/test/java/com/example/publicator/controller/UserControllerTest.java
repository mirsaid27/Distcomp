package com.example.publicator.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/users").then().statusCode(200);
    }

    @Test
    public void testGetUserById() {
        Map<String, String> user = new HashMap<>();
        user.put("login", "lion2");
        user.put("password", "11111111");
        user.put("firstname", "11");
        user.put("lastname", "11");

        Response response = given()
                .contentType("application/json")
                .body(user)
                .when().post("/api/v1.0/users").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/users/{id}").then()
                .body("login", equalTo("lion2"))
                .body("password", equalTo("11111111"))
                .body("firstname", equalTo("11"))
                .body("lastname", equalTo("11"))
                .statusCode(200);
    }

    @Test
    public void testUpdateUserById() {
        Map<String, String> user = new HashMap<>();
        user.put("login", "lion11");
        user.put("password", "11111111");
        user.put("firstname", "11");
        user.put("lastname", "11");
        Map<String, String> newUser = new HashMap<>();
        newUser.put("login", "new11");
        newUser.put("password", "new11111111");
        newUser.put("firstname", "new11");
        newUser.put("lastname", "new11");

        Response response = given()
                .contentType("application/json")
                .body(user)
                .when().post("/api/v1.0/users").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newUser.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newUser)
                .when().put("/api/v1.0/users").then()
                .body("login", equalTo("new11"))
                .body("password", equalTo("new11111111"))
                .body("firstname", equalTo("new11"))
                .body("lastname", equalTo("new11"))
                .statusCode(200);
    }

    @Test
    public void testDeleteUserById() {
        Map<String, String> user = new HashMap<>();
        user.put("login", "lion111");
        user.put("password", "11111111");
        user.put("firstname", "11");
        user.put("lastname", "11");


        Response response = given()
                .contentType("application/json")
                .body(user)
                .when().post("/api/v1.0/users").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/users/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostUserWithBadRequest() {
        Map<String, String> user = new HashMap<>();
        user.put("login", "1");
        user.put("password", "1234567");
        user.put("firstname", "1");
        user.put("lastname", "1");

        given()
                .contentType("application/json")
                .body(user)
                .when().post("/api/v1.0/users").then()
                .body("note", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetUserWithIncorrectId() {
        Map<String, String> user = new HashMap<>();
        user.put("login", "lion1111");
        user.put("password", "11111111");
        user.put("firstname", "11");
        user.put("lastname", "11");


        Response response = given()
                .contentType("application/json")
                .body(user)
                .when().post("/api/v1.0/users").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/users/{id}").then().
                body("note", equalTo("User not found")).
                body("status", equalTo(404))
                .statusCode(404);
    }
}

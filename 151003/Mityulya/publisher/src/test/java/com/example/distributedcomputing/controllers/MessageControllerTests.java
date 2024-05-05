package com.example.distributedcomputing.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MessageControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAll() {
        given()
                .when()
                .get("/api/v1.0/notes")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", commentId)
                .when()
                .get("/api/v1.0/notes/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", commentId)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreate() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + commentId + ", \"content\": \"Updated comment\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/notes")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated comment"));
    }
}
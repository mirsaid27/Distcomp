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
public class TweetRepositoryControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAll() {
        given()
                .when()
                .get("/api/v1.0/storys")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/storys/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/storys/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreate() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + issueId + ", \"editorId\": 7, \"title\": \"updatedTitle699\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/storys")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle699"));
    }
}

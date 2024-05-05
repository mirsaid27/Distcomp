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
public class StickerControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAll() {
        given()
                .when()
                .get("/api/v1.0/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", labelId)
                .when()
                .get("/api/v1.0/tags/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreate() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/tags")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + labelId + ", \"issueId\": 8, \"name\": \"updatedname4529\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/tags")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname4529"));
    }
}

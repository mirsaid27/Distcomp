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
public class CreatorControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetAll() {
        given()
                .when()
                .get("/api/v1.0/editors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"editor2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", editorId)
                .when()
                .get("/api/v1.0/editors/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"editor2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", editorId)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreate() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"editor2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdate() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"editor2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + editorId + ", \"login\": \"updatedEditor109\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/editors")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedEditor109"));
    }

    @Test
    public void testDeleteEditorWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The editor has not been deleted"))
                .body("errorCode", equalTo(403));
    }

    @Test
    public void testSaveEditorWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(400);
    }
}

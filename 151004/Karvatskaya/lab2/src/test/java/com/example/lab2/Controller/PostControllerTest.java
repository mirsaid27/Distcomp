package com.example.lab2.Controller;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class PostControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/posts").then().statusCode(200);
    }

    @Test
    public void testGetPostById() {
        Map<String, String> post = new HashMap<>();
        post.put("issueId", "1");
        post.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(post)
                .when().post("/api/v1.0/posts").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/posts/{id}").then().
                body("issueId", equalTo(1))
                .body("content", equalTo("1111")).statusCode(200);
    }

    @Test
    public void testUpdatePostById() {
        Map<String, String> post = new HashMap<>();
        post.put("issueId", "1");
        post.put("content", "1111");
        Map<String, String> newPost = new HashMap<>();
        newPost.put("id", "0");
        newPost.put("issueId", "1");
        newPost.put("content", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(post)
                .when().post("/api/v1.0/posts").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newPost.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newPost)
                .when().put("/api/v1.0/posts").then()
                .body("issueId", equalTo(1))
                .body("content", equalTo("new1111")).statusCode(200);
    }

    @Test
    public void testDeletePostById() {
        Map<String, String> post = new HashMap<>();
        post.put("issueId", "1");
        post.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(post)
                .when().post("/api/v1.0/posts").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/posts/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostPostWithBadRequest() {
        Map<String, String> post = new HashMap<>();
        post.put("issueId", "1");
        post.put("content", "1");

        given()
                .contentType("application/json")
                .body(post)
                .when().post("/api/v1.0/posts").then()
                .body("message", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetPostWithIncorrectId() {
        Map<String, String> post = new HashMap<>();
        post.put("issueId", "1");
        post.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(post)
                .when().post("/api/v1.0/posts").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/posts/{id}").then().
                body("message", equalTo("Post not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}

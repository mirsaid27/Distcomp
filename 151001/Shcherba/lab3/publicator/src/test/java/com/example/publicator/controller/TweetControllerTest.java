package com.example.publicator.controller;
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
public class TweetControllerTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/tweets").then().statusCode(200);
    }

    @Test
    public void testGetTweetById() {
        Map<String, String> tweet = new HashMap<>();
        tweet.put("userId", "1");
        tweet.put("title", "1111");
        tweet.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(tweet)
                .when().post("/api/v1.0/tweets").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/tweets/{id}").then()
                .body("userId", equalTo(1))
                .body("title", equalTo("1111"))
                .body("content", equalTo("1111"))
                .statusCode(200);
    }

    @Test
    public void testUpdateTweetById() {
        Map<String, String> tweet = new HashMap<>();
        tweet.put("userId", "1");
        tweet.put("title", "1112");
        tweet.put("content", "1111");
        Map<String, String> newTweet = new HashMap<>();
        newTweet.put("userId", "1");
        newTweet.put("title", "new1112");
        newTweet.put("content", "new1111");
        Response response = given()
                .contentType("application/json")
                .body(tweet)
                .when().post("/api/v1.0/tweets").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newTweet.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newTweet)
                .when().put("/api/v1.0/tweets").then()
                .body("userId", equalTo(1))
                .body("title", equalTo("new1112"))
                .body("content", equalTo("new1111"))
                .statusCode(200);
    }

    @Test
    public void testDeleteTweetById() {
        Map<String, String> tweet = new HashMap<>();
        tweet.put("userId", "1");
        tweet.put("title", "1113");
        tweet.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(tweet)
                .when().post("/api/v1.0/tweets").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/tweets/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostTweetWithBadRequest() {
        Map<String, String> tweet = new HashMap<>();
        tweet.put("userId", "1");
        tweet.put("title", "1");
        tweet.put("content", "1");

        given()
                .contentType("application/json")
                .body(tweet)
                .when().post("/api/v1.0/tweets").then()
                .body("note", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetTweetWithIncorrectId() {
        Map<String, String> tweet = new HashMap<>();
        tweet.put("userId", "1");
        tweet.put("title", "1114");
        tweet.put("content", "1111");

        Response response = given()
                .contentType("application/json")
                .body(tweet)
                .when().post("/api/v1.0/tweets").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/tweets/{id}").then().
                body("note", equalTo("Tweet not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}

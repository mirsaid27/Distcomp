package com.example.publicator.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class MarkerControllerTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void basicPingTest() {
        when().request("GET", "/api/v1.0/markers").then().statusCode(200);
    }

    @Test
    public void testGetMarkerById() {
        Map<String, String> marker = new HashMap<>();
        marker.put("tweetId", "1");
        marker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(marker)
                .when().post("/api/v1.0/markers").then()
                .statusCode(201)
                .extract().response();
        System.out.println(response);
        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().get("/api/v1.0/markers/{id}").then()
                //.body("tweetId", equalTo(1))
                .body("name", equalTo("1111"))
                .statusCode(200);
    }

    @Test
    public void testUpdateMarkerById() {
        Map<String, String> marker = new HashMap<>();
        marker.put("tweetId", "1");
        marker.put("name", "1111");
        Map<String, String> newMarker = new HashMap<>();
        newMarker.put("tweetId", "1");
        newMarker.put("name", "new1111");

        Response response = given()
                .contentType("application/json")
                .body(marker)
                .when().post("/api/v1.0/markers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        newMarker.put("id", Integer.toString(id));

        given()
                .contentType("application/json")
                .body(newMarker)
                .when().put("/api/v1.0/markers").then()
                //.body("tweetId", equalTo(1))
                .body("name", equalTo("new1111"))
                .statusCode(200);
    }

    @Test
    public void testDeleteMarkerById() {
        Map<String, String> marker = new HashMap<>();
        marker.put("tweetId", "1");
        marker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(marker)
                .when().post("/api/v1.0/markers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id");
        given().pathParam("id", id).
                when().delete("/api/v1.0/markers/{id}").then()
                .statusCode(204);
    }

    @Test
    public void testPostMarkerWithBadRequest() {
        Map<String, String> marker = new HashMap<>();
        marker.put("tweetId", "1");
        marker.put("name", "1");

        given()
                .contentType("application/json")
                .body(marker)
                .when().post("/api/v1.0/markers").then()
                .body("note", equalTo("Bad request"))
                .body("status", equalTo(400))
                .statusCode(400);
    }

    @Test
    public void testGetMarkerWithIncorrectId() {
        Map<String, String> marker = new HashMap<>();
        marker.put("tweetId", "1");
        marker.put("name", "1111");

        Response response = given()
                .contentType("application/json")
                .body(marker)
                .when().post("/api/v1.0/markers").then()
                .statusCode(201)
                .extract().response();

        int id = response.jsonPath().getInt("id") + 10;
        given().pathParam("id", id).
                when().get("/api/v1.0/markers/{id}").then().
                body("note", equalTo("Marker not found"))
                .body("status", equalTo(404))
                .statusCode(404);
    }
}

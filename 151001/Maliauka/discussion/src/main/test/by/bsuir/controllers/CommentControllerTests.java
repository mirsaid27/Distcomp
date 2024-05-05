package by.bsuir.controllers;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommentControllerTests {
    private final Header header = new Header("Accept-Language","ru,ru-RU;q=0.9,en;q=0.8,en-US;q=0.7,uk;q=0.6,und;q=0.5,de;q=0.4");
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24130;
    }

    @Test
    public void testGetComments() {
        given()
                .when()
                .header(header)
                .get("/api/v1.0/comments")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCommentById() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .header(header)
                .body(body)
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(200)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", commentId)
                .header(header)
                .when()
                .get("/api/v1.0/comments/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", commentId)
                .header(header)
                .when()
                .delete("/api/v1.0/comments/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteComment() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(200)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", commentId)
                .header(header)
                .when()
                .delete("/api/v1.0/comments/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateComment() {
        String body = "{\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"content6588\"\n" +
                "}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .post("/api/v1.0/comments")
                .then()
                .statusCode(200)
                .extract().response();

        long commentId = response.jsonPath().getLong("id");

        body = "{ \"id\": " + commentId + ",\n" +
                "    \"issueId\": 434,\n" +
                "    \"content\": \"Updated comment\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .header(header)
                .when()
                .put("/api/v1.0/comments")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated comment"));

        given()
                .pathParam("id", commentId)
                .header(header)
                .when()
                .delete("/api/v1.0/comments/{id}")
                .then()
                .statusCode(200);
    }
}

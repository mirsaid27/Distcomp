package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TweetControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetTweets() {
        given()
                .when()
                .get("/api/v1.0/tweets")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTweetById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"UserId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", tweetId)
                .when()
                .get("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteTweet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"UserId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", tweetId)
                .when()
                .delete("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveTweet() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"UserId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateTweet() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"UserId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(201)
                .extract().response();

        long tweetId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + tweetId + ", \"UserId\": 7, \"title\": \"updatedTitle699\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/tweets")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle699"));
    }

    @Test
    public void testGetTweetByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Tweet not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteTweetWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/tweets/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The tweet has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testSaveTweetWithWrongTitle() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"UserId\": 7, \"title\": \"x\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/tweets")
                .then()
                .statusCode(400);
    }
}

package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StoryControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetStories() {
        given()
                .when()
                .get("/api/v1.0/storys")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetStoryById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", storyId)
                .when()
                .get("/api/v1.0/storys/{id}")
                .then()
                .statusCode(200);
        given()
                .pathParam("id", storyId)
                .when()
                .delete("/api/v1.0/storys/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteStory() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", storyId)
                .when()
                .delete("/api/v1.0/storys/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateStory() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(201)
                .extract().response();

        long storyId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + storyId + ", \"title\": \"updatedTitle69994\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/storys")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle69994"));
        given()
                .pathParam("id", storyId)
                .when()
                .delete("/api/v1.0/storys/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetStoryByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/storys/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Story not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteStoryWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/storys/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Story not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testSaveStoryWithWrongTitle() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"x\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/storys")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetStoryByContentCriteria() {
        String uri = "/api/v1.0/storys/byCriteria?content=" + "content123";
        String firstContent = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("content123", firstContent);
    }

    @Test
    public void testGetStoryByAuthorLoginCriteria() {
        String uri = "/api/v1.0/storys/byCriteria?authorLogin=" + "12345";
        int firstContent = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].authorId");

        assertEquals(208, firstContent);
    }
}

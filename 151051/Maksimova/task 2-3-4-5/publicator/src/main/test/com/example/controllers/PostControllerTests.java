package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PostControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetPosts() {
        given()
                .when()
                .get("/api/v1.0/posts")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetPostById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Post content\" }")
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", postId)
                .when()
                .get("/api/v1.0/posts/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", postId)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeletePost() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Post content\" }")
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", postId)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdatePost() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"content\": \"Post content\" }")
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + postId + ", \"content\": \"Updated content\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/posts")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated content"));

        given()
                .pathParam("id", postId)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetPostByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/posts/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Post not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeletePostWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Post not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testFindAllOrderById() {
        String body = "{ \"content\": \"Post content 1\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId1 = response.jsonPath().getLong("id");

        body = "{ \"content\": \"Post content 2\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId2 = response.jsonPath().getLong("id");

        String uri = "/api/v1.0/posts?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        long id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(postId1, id);
        uri = "/api/v1.0/posts?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(postId2, id);

        given()
                .pathParam("id", postId1)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", postId2)
                .when()
                .delete("/api/v1.0/posts/{id}")
                .then()
                .statusCode(204);
    }
}


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
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
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
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        long postId = response.jsonPath().getLong("id");

        body = "{ \"id\": " + postId + ", \"content\": \"Updated post\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/posts")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated post"));

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
    public void testGetPostByIssueId() {
        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"title6789\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = issueResponse.jsonPath().getLong("id");

        String body = "{ \"content\": \"Test content\", \"issueId\":  " + issueId + "}";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/posts/byIssue/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetPostByIssueIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/posts/byIssue/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Post not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testFindAllOrderById(){
        String body = "{ \"content\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        Integer postId1 = response.jsonPath().getInt("id");

        body = "{ \"content\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        Integer postId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/posts?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        Integer id = given()
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

    @Test
    public void testFindAllOrderByContent(){
        String body = "{ \"content\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        Integer postId1 = response.jsonPath().getInt("id");

        body = "{ \"content\": \"zzzzzzzzzzzzz\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/posts")
                .then()
                .statusCode(201)
                .extract().response();

        Integer postId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/posts?pageNumber=0&pageSize=10&sortBy=content&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("aaa", content);
        uri = "/api/v1.0/posts?pageNumber=0&pageSize=10&sortBy=content&sortOrder=desc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("zzzzzzzzzzzzz", content);

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

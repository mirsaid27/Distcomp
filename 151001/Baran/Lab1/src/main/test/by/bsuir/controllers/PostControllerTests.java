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
                .get("/api/v1.0/Posts")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetPostById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/Posts")
                .then()
                .statusCode(201)
                .extract().response();

        long PostId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", PostId)
                .when()
                .get("/api/v1.0/Posts/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeletePost() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/Posts")
                .then()
                .statusCode(201)
                .extract().response();

        long PostId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", PostId)
                .when()
                .delete("/api/v1.0/Posts/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSavePost() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/Posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdatePost() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"content\": \"Test content\" }")
                .when()
                .post("/api/v1.0/Posts")
                .then()
                .statusCode(201)
                .extract().response();

        long PostId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + PostId + ", \"content\": \"Updated Post\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/Posts")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated Post"));
    }

    @Test
    public void testGetPostByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/Posts/{id}")
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
                .delete("/api/v1.0/Posts/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Post has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testGetPostByIssueId() {
        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 5, \"title\": \"title3190\", \"content\": \"content9594\" }")
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
                .post("/api/v1.0/Posts")
                .then()
                .statusCode(201)
                .extract().response();


        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/Posts/byIssue/{id}")
                .then()
                .statusCode(200)
                .body("content", equalTo("Test content"));
    }

    @Test
    public void testGetPostByIssueIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/Posts/byIssue/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Post not found!"))
                .body("errorCode", equalTo(40004));
    }
}

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
public class IssueControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetIssues() {
        given()
                .when()
                .get("/api/v1.0/issues")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetIssueById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/issues/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteIssue() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveIssue() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateIssue() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + issueId + ", \"editorId\": 7, \"title\": \"updatedTitle699\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/issues")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle699"));
    }

    @Test
    public void testGetIssueByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/issues/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Issue not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteIssueWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The issue has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testSaveIssueWithWrongTitle() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"x\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetIssueByTitleAndContentCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        String title = response.jsonPath().getString("title");
        String content = response.jsonPath().getString("content");
        String uri = "/api/v1.0/issues/byCriteria?title=" + title + "&content=" + content;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("content", equalTo(content));
    }

    @Test
    public void testGetIssueByTagIdCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\",\"TagId\": 10 }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        String title = response.jsonPath().getString("title");
        String content = response.jsonPath().getString("content");
        long TagId = response.jsonPath().getLong("TagId");
        String uri = "/api/v1.0/issues/byCriteria?TagId=" + TagId;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .body("title", equalTo(title))
                .body("content", equalTo(content));
    }

    @Test
    public void testGetIssueByWrongTagIdCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\",\"TagId\": 10 }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long TagId = response.jsonPath().getLong("TagId") + 2;
        String uri = "/api/v1.0/issues/byCriteria?TagId=" + TagId;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(400)
                .body("errorCode", equalTo(40005))
                .body("errorMessage", equalTo("Issue not found!"));
    }

    @Test
    public void testGetIssueByWrongTitleAndContentCriteria() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"editorId\": 7, \"title\": \"title3190\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        String title = response.jsonPath().getString("title") + "aboba";
        String content = response.jsonPath().getString("content") + "mama";
        String uri = "/api/v1.0/issues/byCriteria?title=" + title + "&content=" + content;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(400)
                .body("errorCode", equalTo(40005))
                .body("errorMessage", equalTo("Issue not found!"));
    }
}

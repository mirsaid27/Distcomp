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
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
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
        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteIssue() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
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
    public void testUpdateIssue() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"newTitle\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + issueId + ", \"title\": \"updatedTitle69994\", \"content\": \"updatedContent9402\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/issues")
                .then()
                .statusCode(200)
                .body("title", equalTo("updatedTitle69994"));
        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(204);
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
                .body("errorMessage", equalTo("Issue not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testSaveIssueWithWrongTitle() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"title\": \"x\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetIssueByContentCriteria() {
        String uri = "/api/v1.0/issues/byCriteria?content=" + "content123";
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
    public void testGetIssueByEditorLoginCriteria() {
        String uri = "/api/v1.0/issues/byCriteria?editorLogin=" + "12345";
        int firstContent = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].editorId");

        assertEquals(208, firstContent);
    }
}

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
public class TagControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetTags() {
        given()
                .when()
                .get("/api/v1.0/Tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTagById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/Tags")
                .then()
                .statusCode(201)
                .extract().response();

        long TagId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", TagId)
                .when()
                .get("/api/v1.0/Tags/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteTag() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/Tags")
                .then()
                .statusCode(201)
                .extract().response();

        long TagId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", TagId)
                .when()
                .delete("/api/v1.0/Tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveTag() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/Tags")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateTag() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"issueId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/Tags")
                .then()
                .statusCode(201)
                .extract().response();

        long TagId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + TagId + ", \"issueId\": 8, \"name\": \"updatedname4529\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/Tags")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname4529"));
    }

    @Test
    public void testGetTagByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/Tags/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Tag not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteTagWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/Tags/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The Tag has not been deleted"))
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

        String body = "{ \"issueId\": " + issueId + ", \"name\": \"name4845\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/Tags")
                .then()
                .statusCode(201)
                .extract().response();


        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/Tags/byIssue/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("name4845"));
    }

    @Test
    public void testGetTagByIssueIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/Tags/byIssue/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Tag not found!"))
                .body("errorCode", equalTo(40004));
    }
}


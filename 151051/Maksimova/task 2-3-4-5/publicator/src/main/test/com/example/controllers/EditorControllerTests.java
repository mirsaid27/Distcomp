package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EditorControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetEditors() {
        given()
                .when()
                .get("/api/v1.0/editors")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetEditorById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newEditor\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", editorId)
                .when()
                .get("/api/v1.0/editors/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", editorId)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteEditor() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newEditor\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", editorId)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateEditor() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newEditor\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + editorId + ", \"login\": \"updatedEditor1091\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/editors")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedEditor1091"));

        given()
                .pathParam("id", editorId)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetEditorByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/editors/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Editor not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteEditorWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Editor not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testSaveEditorWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(400);
    }

    @Test
    public void testGetEditorByIssueId() {
        Response editorResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"newEditor\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        long editorId = editorResponse.jsonPath().getLong("id");
        String body = "{ \"editorId\": "+ editorId +", \"title\": \"newTitle123\", \"content\": \"content9594\" }";

        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = issueResponse.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/editors/byIssue/{id}")
                .then()
                .statusCode(200)
                .body("login", equalTo("newEditor"));

        given()
                .pathParam("id", editorId)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", issueId)
                .when()
                .delete("/api/v1.0/issues/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindAllOrderById(){
        String body = "{ \"login\": \"111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer editorId1 = response.jsonPath().getInt("id");

        body = "{ \"login\": \"zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer editorId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/editors?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        Integer content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(editorId2, content);

        given()
                .pathParam("id", editorId1)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", editorId2)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindAllOrderByLogin(){
        String body = "{ \"login\": \"111\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer editorId1 = response.jsonPath().getInt("id");

        body = "{ \"login\": \"zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/editors")
                .then()
                .statusCode(201)
                .extract().response();

        Integer editorId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/editors?pageNumber=0&pageSize=10&sortBy=login&sortOrder=desc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].login");

        assertEquals("zzzzz-is-very-very-very-very-very-very-long-more-than-64-symbols", content);

        uri = "/api/v1.0/editors?pageNumber=0&pageSize=10&sortBy=login&sortOrder=asc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].login");

        assertEquals("111", content);

        given()
                .pathParam("id", editorId1)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", editorId2)
                .when()
                .delete("/api/v1.0/editors/{id}")
                .then()
                .statusCode(204);
    }
}

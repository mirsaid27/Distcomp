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
public class NoteControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetNotes() {
        given()
                .when()
                .get("/api/v1.0/notes")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetNoteById() {
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long noteId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", noteId)
                .when()
                .get("/api/v1.0/notes/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", noteId)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteNote() {
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long noteId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", noteId)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateNote() {
        String body = "{ \"content\": \"Test content\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        long noteId = response.jsonPath().getLong("id");

        body = "{ \"id\": " + noteId + ", \"content\": \"Updated note\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/notes")
                .then()
                .statusCode(200)
                .body("content", equalTo("Updated note"));

        given()
                .pathParam("id", noteId)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetNoteByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/notes/{id}")
                .then()
                .statusCode(400)
                .body("errorNote", equalTo("Note not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteNoteWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(400)
                .body("errorNote", equalTo("Note not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testGetNoteByTweetIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/notes/byTweet/{id}")
                .then()
                .statusCode(400)
                .body("errorNote", equalTo("Note not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testFindAllOrderById(){
        String body = "{ \"content\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        Integer noteId1 = response.jsonPath().getInt("id");

        body = "{ \"content\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        Integer noteId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/notes?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        Integer id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        uri = "/api/v1.0/notes?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(noteId2, id);

        given()
                .pathParam("id", noteId1)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", noteId2)
                .when()
                .delete("/api/v1.0/notes/{id}")
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
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        Integer noteId1 = response.jsonPath().getInt("id");

        body = "{ \"content\": \"zzzzzzzzzzzzz\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/notes")
                .then()
                .statusCode(201)
                .extract().response();

        Integer noteId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/notes?pageNumber=0&pageSize=10&sortBy=content&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].content");

        assertEquals("aaa", content);
        uri = "/api/v1.0/notes?pageNumber=0&pageSize=10&sortBy=content&sortOrder=desc";
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
                .pathParam("id", noteId1)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", noteId2)
                .when()
                .delete("/api/v1.0/notes/{id}")
                .then()
                .statusCode(204);
    }
}

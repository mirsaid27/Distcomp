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
                .get("/api/v1.0/tags")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetTagById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", tagId)
                .when()
                .get("/api/v1.0/tags/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", tagId)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteTag() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", tagId)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateTag() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"name4845\" }")
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + tagId + ", \"issueId\": 8, \"name\": \"updatedname45295\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/tags")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname45295"));

        given()
                .pathParam("id", tagId)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetTagByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/tags/{id}")
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
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Tag not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testGetTagByIssueId() {
        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"title3194650\", \"content\": \"content9594\" }")
                .when()
                .issue("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = issueResponse.jsonPath().getLong("id");

        String body = "{ \"issueId\": " + issueId + ", \"name\": \"name4845\" }";

        Response tagResponse = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        long tagId = tagResponse.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/tags/byIssue/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", tagId)
                .when()
                .delete("/api/v1.0/tags/{id}")
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
        String body = "{ \"name\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        Integer tagId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        Integer tagId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/tags?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        Integer id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(tagId1, id);
        uri = "/api/v1.0/tags?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(tagId2, id);

        given()
                .pathParam("id", tagId1)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", tagId2)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindAllOrderByContent(){
        String body = "{ \"name\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        Integer tagId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .issue("/api/v1.0/tags")
                .then()
                .statusCode(201)
                .extract().response();

        Integer tagId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/tags?pageNumber=0&pageSize=10&sortBy=name&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].name");

        assertEquals("aaa", content);
        uri = "/api/v1.0/tags?pageNumber=0&pageSize=10&sortBy=name&sortOrder=desc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].name");

        assertEquals("bbb", content);

        given()
                .pathParam("id", tagId1)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", tagId2)
                .when()
                .delete("/api/v1.0/tags/{id}")
                .then()
                .statusCode(204);
    }
}


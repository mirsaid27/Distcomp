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
public class LabelControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetLabels() {
        given()
                .when()
                .get("/api/v1.0/labels")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetLabelById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", labelId)
                .when()
                .get("/api/v1.0/labels/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteLabel() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateLabel() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + labelId + ", \"issueId\": 8, \"name\": \"updatedname45295\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/labels")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname45295"));

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetLabelByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/labels/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Label not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteLabelWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Label not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testGetLabelByIssueId() {
        Response issueResponse = given()
                .contentType(ContentType.JSON)
                .body("{ \"title\": \"title3194650\", \"content\": \"content9594\" }")
                .when()
                .post("/api/v1.0/issues")
                .then()
                .statusCode(201)
                .extract().response();

        long issueId = issueResponse.jsonPath().getLong("id");

        String body = "{ \"issueId\": " + issueId + ", \"name\": \"name4845\" }";

        Response labelResponse = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        long labelId = labelResponse.jsonPath().getLong("id");

        given()
                .pathParam("id", issueId)
                .when()
                .get("/api/v1.0/labels/byIssue/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", labelId)
                .when()
                .delete("/api/v1.0/labels/{id}")
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
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        Integer labelId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        Integer labelId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/labels?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        Integer id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(labelId1, id);
        uri = "/api/v1.0/labels?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(labelId2, id);

        given()
                .pathParam("id", labelId1)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", labelId2)
                .when()
                .delete("/api/v1.0/labels/{id}")
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
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        Integer labelId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/labels")
                .then()
                .statusCode(201)
                .extract().response();

        Integer labelId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/labels?pageNumber=0&pageSize=10&sortBy=name&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].name");

        assertEquals("aaa", content);
        uri = "/api/v1.0/labels?pageNumber=0&pageSize=10&sortBy=name&sortOrder=desc";
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
                .pathParam("id", labelId1)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", labelId2)
                .when()
                .delete("/api/v1.0/labels/{id}")
                .then()
                .statusCode(204);
    }
}


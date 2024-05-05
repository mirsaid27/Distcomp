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
public class MarkerControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetMarkers() {
        given()
                .when()
                .get("/api/v1.0/markers")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetMarkerById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        long markerId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", markerId)
                .when()
                .get("/api/v1.0/markers/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", markerId)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteMarker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        long markerId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", markerId)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateMarker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        long markerId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + markerId + ", \"tweetId\": 8, \"name\": \"updatedname45295\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/markers")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname45295"));

        given()
                .pathParam("id", markerId)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testGetMarkerByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/markers/{id}")
                .then()
                .statusCode(400)
                .body("errorNote", equalTo("Marker not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteMarkerWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(400)
                .body("errorNote", equalTo("Marker not found!"))
                .body("errorCode", equalTo(40004));
    }



    @Test
    public void testFindAllOrderById(){
        String body = "{ \"name\": \"aaa\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        Integer markerId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        Integer markerId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/markers?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        Integer id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        uri = "/api/v1.0/markers?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(markerId2, id);

        given()
                .pathParam("id", markerId1)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", markerId2)
                .when()
                .delete("/api/v1.0/markers/{id}")
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
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        Integer markerId1 = response.jsonPath().getInt("id");

        body = "{ \"name\": \"bbb\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        Integer markerId2 = response.jsonPath().getInt("id");
        String uri = "/api/v1.0/markers?pageNumber=0&pageSize=10&sortBy=name&sortOrder=asc";
        String content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].name");

        assertEquals("aaa", content);
        uri = "/api/v1.0/markers?pageNumber=0&pageSize=10&sortBy=name&sortOrder=desc";
        content = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].name");

        assertEquals("name4845", content);

        given()
                .pathParam("id", markerId1)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", markerId2)
                .when()
                .delete("/api/v1.0/markers/{id}")
                .then()
                .statusCode(204);
    }
}


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
                .body("{ \"tweetId\": 8, \"name\": \"name4845\" }")
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
    }

    @Test
    public void testDeleteMarker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"tweetId\": 8, \"name\": \"name4845\" }")
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
    public void testSaveMarker() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"tweetId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateMarker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"tweetId\": 8, \"name\": \"name4845\" }")
                .when()
                .post("/api/v1.0/markers")
                .then()
                .statusCode(201)
                .extract().response();

        long markerId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + markerId + ", \"tweetId\": 8, \"name\": \"updatedname4529\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/markers")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedname4529"));
    }

    @Test
    public void testGetMarkerByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/markers/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("Marker not found!"))
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
                .body("errorMessage", equalTo("The marker has not been deleted"))
                .body("errorCode", equalTo(40003));
    }
}


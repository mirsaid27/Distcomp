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
public class StickerControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetStickers() {
        given()
                .when()
                .get("/api/v1.0/stickers")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetStickerById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"sticker1\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", stickerId)
                .when()
                .get("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"sticker2\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateSticker() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"sticker1\" }")
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + stickerId + ", \"name\": \"updatedSticker\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/stickers")
                .then()
                .statusCode(200)
                .body("name", equalTo("updatedSticker"));

        given()
                .pathParam("id", stickerId)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testFindAllStickersOrderById() {
        String body = "{ \"name\": \"sticker1\"}";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId1 = response.jsonPath().getLong("id");

        body = "{ \"name\": \"sticker2\"}";
        response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1.0/stickers")
                .then()
                .statusCode(201)
                .extract().response();

        long stickerId2 = response.jsonPath().getLong("id");

        String uri = "/api/v1.0/stickers?pageNumber=0&pageSize=10&sortBy=id&sortOrder=asc";
        long id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(stickerId1, id);
        uri = "/api/v1.0/stickers?pageNumber=0&pageSize=10&sortBy=id&sortOrder=desc";
        id = given()
                .contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .statusCode(200)
                .extract()
                .path("[0].id");

        assertEquals(stickerId2, id);

        given()
                .pathParam("id", stickerId1)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);

        given()
                .pathParam("id", stickerId2)
                .when()
                .delete("/api/v1.0/stickers/{id}")
                .then()
                .statusCode(204);
    }
}

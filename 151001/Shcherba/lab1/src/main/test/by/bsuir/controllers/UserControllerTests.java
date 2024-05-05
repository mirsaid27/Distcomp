package by.bsuir.controllers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTests {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
    }

    @Test
    public void testGetusers() {
        given()
                .when()
                .get("/api/v1.0/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUserById() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"User2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long UserId = response.jsonPath().getLong("id");
        given()
                .pathParam("id", UserId)
                .when()
                .get("/api/v1.0/users/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"User2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long UserId = response.jsonPath().getLong("id");

        given()
                .pathParam("id", UserId)
                .when()
                .delete("/api/v1.0/users/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testSaveUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"User2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateUser() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"User2019\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(201)
                .extract().response();

        long UserId = response.jsonPath().getLong("id");

        String body = "{ \"id\": " + UserId + ", \"login\": \"updatedUser109\", \"password\": \"updatedPass5907\", \"firstname\": \"updatedFirstname7007\", \"lastname\": \"updatedLastname3768\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/v1.0/users")
                .then()
                .statusCode(200)
                .body("login", equalTo("updatedUser109"));
    }

    @Test
    public void testGetUserByIdWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .get("/api/v1.0/users/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("User not found!"))
                .body("errorCode", equalTo(40004));
    }

    @Test
    public void testDeleteUserWithWrongArgument() {
        given()
                .pathParam("id", 999999)
                .when()
                .delete("/api/v1.0/users/{id}")
                .then()
                .statusCode(400)
                .body("errorMessage", equalTo("The User has not been deleted"))
                .body("errorCode", equalTo(40003));
    }

    @Test
    public void testSaveUserWithWrongLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"login\": \"x\", \"password\": \"pass6459\", \"firstname\": \"firstname4155\", \"lastname\": \"lastname7290\" }")
                .when()
                .post("/api/v1.0/users")
                .then()
                .statusCode(400);
    }
}

package api.v1;

import by.bsuir.dc.impl.author.model.Author;
import by.bsuir.dc.impl.author.model.AuthorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthorApiTest extends BaseApiTest {
    Author author = new Author("login", "password", "first", "last");
    @Test
    public void createAuthorTest() {
        createAuthor();
    }
    @SneakyThrows
    @Test
    @Order(2)
    public void getAllAuthorsTest() {
        author.setId(createAuthor());
        given()
            .when()
            .get("/authors")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(notNullValue())
        ;
    }
    @SneakyThrows
    @Test
    @Order(3)
    public void getAuthorTest() {
        author.setId(createAuthor());
        given()
            .when()
            .get("/authors/" + author.getId())
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(equalTo(new ObjectMapper().writeValueAsString(author)))
        ;
    }

    @SneakyThrows
    @Test
    @Order(4)
    public void updateAuthorTest() {
        author.setId(createAuthor());
        AuthorRequest author_updated = new AuthorRequest( author.getId(), "login_upd", "password_upd", "first_upd", "last_upd");
        given()
            .contentType(ContentType.JSON)
            .body(author_updated)
            .when()
            .put("/authors")
            .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body(equalTo(new ObjectMapper().writeValueAsString(author_updated)))
        ;
    }
    @Test
    @Order(5)
    public void deleteAuthorTest() {
        author.setId(createAuthor());
        given()
            .when()
            .delete("/authors/" + author.getId())
            .then()
            .statusCode(204);
    }

    public long createAuthor() {
        ValidatableResponse result = given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .post("/authors")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("login", equalTo(author.getLogin()))
                .body("password", equalTo(author.getPassword()))
                .body("firstname", equalTo(author.getFirstname()))
                .body("lastname", equalTo(author.getLastname()))
                ;
        return result.extract().body().path("id");
    }
}

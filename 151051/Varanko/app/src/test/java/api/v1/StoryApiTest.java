package api.v1;

import by.bsuir.dc.impl.story.model.Story;
import by.bsuir.dc.impl.story.model.StoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StoryApiTest extends BaseApiTest {
    Story story = new Story(1, "title", "content");
    @Test
    public void createStoryTest() {
        createStory();
    }
    @SneakyThrows
    @Test
    public void getAllAuthorsTest() {
        story.setId(createStory());
        given()
            .when()
            .get("/stories")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(notNullValue())
        ;
    }
    @SneakyThrows
    @Test
    public void getAuthorTest() {
        story.setId(createStory());
        given()
            .when()
            .get("/stories/" + story.getId())
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(equalTo(new ObjectMapper().writeValueAsString(story)))
        ;
    }

    @SneakyThrows
    @Test
    public void updateStoryTest() {
        story.setId(createStory());
        StoryRequest story_updated = new StoryRequest( story.getId(), 2L, "title_upd", "content_upd", LocalDateTime.now(), LocalDateTime.now());
        given()
            .contentType(ContentType.JSON)
            .body(story_updated)
            .when()
            .put("/stories")
            .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body(equalTo(new ObjectMapper().writeValueAsString(story_updated)))
        ;
    }
    @Test
    public void deleteStoryTest() {
        story.setId(createStory());
        given()
            .when()
            .delete("/stories/" + story.getId())
            .then()
            .statusCode(204);
    }

    public long createStory() {
        ValidatableResponse result = given()
                .contentType(ContentType.JSON)
                .body(story)
                .when()
                .post("/stories")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("authorId", equalTo(story.getAuthorId()))
                .body("title", equalTo(story.getTitle()))
                .body("content", equalTo(story.getContent()))
                ;
        return result.extract().body().path("id");
    }
}

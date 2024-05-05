package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.dao.TweetRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate5.HibernateSystemException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Name Surname
 * 
 */

public class TweetControllerTest extends AbstractControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TweetRepository tweetRepository;

    @Test
    @Order(1)
    public void insertTweets() throws Exception {
	mockMvc.perform(
		post("/api/v1.0/tweets")
		    .contentType(MediaType.APPLICATION_JSON)
		    .content("""
			{
			  "title": "The 5 top presidents of USA",
			  "content": "The most popular president of the USA is Yosef Stalin! ",
			  "userId": 3
			}
			""")
	    )
	    .andExpectAll(
		status().is(201)
	    );
    }

    @Test
    @Order(2)
    public void duplicateTweetTest() throws Exception {
	Mockito.when(tweetRepository.save(any()))
	    .thenThrow(new HibernateSystemException(null));
	mockMvc.perform(
	    post("/api/v1.0/tweets")
		.contentType(MediaType.APPLICATION_JSON)
		.content("""
		    {
		       "title": "The 5 top presidents of USA",
		       "content": "Not! Trump is the best president! ",
		       "userId": 2
		    }
		          """)
	).andExpectAll(
	    status().is(403)
	);
    }


    //    @Test
    @Order(3)
    public void deleteCommentTest() throws Exception {
	//retrieve data base content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/tweets/1/comments")
	    .then().assertThat()
	    .status(HttpStatus.OK)
	    .body("id", hasItems(1, 2));
	//first remove of content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .delete("/api/v1.0/comments/1")
	    .then().assertThat()
	    .status(HttpStatus.NO_CONTENT);
	//second remove of content
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .delete("/api/v1.0/comments/1")
	    .then().assertThat()
	    .status(HttpStatus.NOT_FOUND);
	//retrieve data again
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/tweets/1/comments")
	    .then().assertThat()
	    .status(HttpStatus.OK)
	    .body("id", hasItems(2));
    }


    @Test
    @Order(4)
    public void databaseConnectionFailedTest() throws Exception {
	Mockito.when(tweetRepository.findById(anyLong()))
	    .thenReturn(Optional.empty());
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/tweets/1")
	    .then().assertThat()
	    .status(HttpStatus.NOT_FOUND);
	Mockito.when(tweetRepository.findById(anyLong()))
	    .thenThrow(new HibernateSystemException(null));
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .get("/api/v1.0/tweets/1")
	    .then().assertThat()
	    .status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

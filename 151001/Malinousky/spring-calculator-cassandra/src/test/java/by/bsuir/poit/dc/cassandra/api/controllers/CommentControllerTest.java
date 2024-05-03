package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.api.dto.response.CommentDto;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


/**
 * @author Name Surname
 * 
 */
@SpringBootTest(classes = WebTestConfiguration.class)
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles({"test-cassandra"})
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final String KEYSPACE_NAME = "test";
    @Container
    public static final CassandraContainer<?> cassandra =
	(CassandraContainer<?>) new CassandraContainer("cassandra:3.11.3")
				    .withExposedPorts(9042);


    @BeforeAll
    static void setupCassandraConnectionProperties() {
	System.setProperty("CASSANDRA_KEYSPACE_NAME", KEYSPACE_NAME);
	System.setProperty("CASSANDRA_CONTACT_POINTS", cassandra.getHost());
	System.setProperty("CASSANDRA_PORT", String.valueOf(cassandra.getMappedPort(9042)));
	System.setProperty("CASSANDRA_DATACENTER", cassandra.getLocalDatacenter());
	createKeyspace(cassandra.getCluster());
	cassandra.start();
    }

    @AfterAll
    static void finish() {
	cassandra.stop();
    }

    static void createKeyspace(Cluster cluster) {
	try (Session session = cluster.connect()) {
	    session.execute(STR."CREATE KEYSPACE IF NOT EXISTS \{KEYSPACE_NAME} WITH replication = \n{'class':'SimpleStrategy','replication_factor':'1'};");
	}
    }

    @Test
    @Order(1)
    void checkContainerIsRunning() {
	assertTrue(cassandra.isRunning());
    }

    @Test
    @Order(2)
    void saveComment() {
	given()
	    .mockMvc(mockMvc)
	    .contentType(ContentType.JSON)
	    .body("""
		      {
			    "tweetId": 1,
			    "content": "The very long text"
		      }
		""")
	    .post("/api/v1.0/comments")
	    .then().assertThat()
	    .status(HttpStatus.CREATED)
	    .body("tweetId", equalTo(1))
	    .body("content", equalTo("The very long text"));
    }

    @Test
    @Order(3)
    void checkUpdateTest() {
	CommentDto dto = given()
			  .mockMvc(mockMvc)
			  .contentType(ContentType.JSON)
			  .body("""
			          {
			      		"tweetId": 1,
			      		"content": "The another very long text"
			          } 
			      """)
			  .post("/api/v1.0/comments")
			  .then().assertThat()
			  .status(HttpStatus.CREATED)
			  .body("tweetId", equalTo(1))
			  .body("content", equalTo("The another very long text"))
			  .extract()
			  .as(CommentDto.class);
	//check that id was changed
	given()
	    .mockMvc(mockMvc)
	    .contentType(ContentType.JSON)
	    .body(STR."""
		   			{
		   			"id":\{dto.id()},
					"tweetId":1,
					"content":"T"
			}
		""")
	    .put("/api/v1.0/comments")
	    .then().assertThat()
	    .status(HttpStatus.BAD_REQUEST);
	given()
	    .mockMvc(mockMvc)
	    .get(STR."/api/v1.0/comments/\{dto.id()}")
	    .then().assertThat()
	    .status(HttpStatus.OK);
	given()
	    .mockMvc(mockMvc)
	    .delete(STR."/api/v1.0/comments/\{dto.id()}")
	    .then().assertThat()
	    .status(HttpStatus.NO_CONTENT);
	given()
	    .mockMvc(mockMvc)
	    .delete(STR."/api/v1.0/comments/\{dto.id()}")
	    .then().assertThat()
	    .status(HttpStatus.NOT_FOUND);
    }
}

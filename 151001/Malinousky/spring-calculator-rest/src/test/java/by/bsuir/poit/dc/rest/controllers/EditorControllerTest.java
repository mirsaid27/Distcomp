package by.bsuir.poit.dc.rest.controllers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Name Surname
 * 
 */
public class EditorControllerTest extends AbstractControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(0)
    public void JsonTest() throws Exception {
	mockMvc.perform(get("/api/v1.0/users/1"))
	    .andExpectAll(
		status().is2xxSuccessful(),
		content().json("""
		       {
		    "id": 1,
		    "login": "m_rgan",
		    "firstname": "Morgan",
		    "lastname": "Paulsen"
		       }
		       """)
	    );
    }

    @Test
    @Order(1)
    public void findAllUsers_ReturnUserList() throws Exception {
	given()
	    .mockMvc(mockMvc)
	    .when()
	    .delete("/api/v1.0/users/1")
	    .then().assertThat()
	    .status(HttpStatus.FORBIDDEN);
    }
}

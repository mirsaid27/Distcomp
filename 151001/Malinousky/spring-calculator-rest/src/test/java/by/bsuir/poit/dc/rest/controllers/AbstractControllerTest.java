package by.bsuir.poit.dc.rest.controllers;

import by.bsuir.poit.dc.rest.TestContainersConfiguration;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/**
 * @author Name Surname
 * 
 */
@DirtiesContext
@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestContainersConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class AbstractControllerTest {

}

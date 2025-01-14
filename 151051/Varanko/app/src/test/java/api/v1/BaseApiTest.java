package api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseApiTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 24110;
        RestAssured.basePath = "/api/v1.0";
    }
}

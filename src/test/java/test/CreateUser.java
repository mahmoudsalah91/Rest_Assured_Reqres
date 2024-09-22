package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import payload.User;
import java.io.File;
import java.io.IOException;
import static org.hamcrest.Matchers.*;

public class CreateUser {
    public Logger logger = LogManager.getLogger(CreateUser.class);
    User userPayload = new User();
    ObjectMapper objectMapper;
    public static JsonNode jsonNode;

    @Test
    public void testPostUser(ITestContext context) throws IOException {
        try {
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File(System.getProperty("user.dir") + "/TestData/postBody.json"));
            String name = jsonNode.get("name").asText();
            String job = jsonNode.get("job").asText();

            Response response = UserEndPoints.createUser();

            response.then()
                    .statusCode(201)

                    .body("name", equalTo(name))
                    .body("job", equalTo(job))
                    .log().all();
            context.getSuite().setAttribute("user_id", userPayload.getId());

        } catch (IOException e) {
            logger.error("Network error occurred: {}", e.getMessage());

        }catch (Exception e){

            logger.debug("Failed to create user as :{}", e.getMessage());
        }
    }
}

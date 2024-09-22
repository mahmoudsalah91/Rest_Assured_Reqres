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

public class UpdateUser {
    private static final Logger logger = LogManager.getLogger(UpdateUser.class);
    User userPayload = new User();
    ObjectMapper objectMapper;
    public static JsonNode jsonNode;


    @Test
    public void testUpdateUser(ITestContext context) throws IOException {
        try {
            int id = (int) context.getSuite().getAttribute("user_id");
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File(System.getProperty("user.dir") + "/TestData/updateBody.json"));
            String name = jsonNode.get("name").asText();
            String job = jsonNode.get("job").asText();

            Response response = UserEndPoints.updateUser();
            response.then()
                    .statusCode(200)
                    .log().all()
                    .body("name", equalTo(name))
                    .body("job", equalTo(job));
        } catch (IOException e) {
            logger.error("Network error occurred: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.debug("Failed to update user as : {}", e.getMessage());
        }
    }
}

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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;


public class GetUser {
    private static final Logger logger = LogManager.getLogger(GetUser.class);
    User userPayload = new User();
    ObjectMapper objectMapper;
    public static  JsonNode jsonNode;
    //test will fail because the based on API structured not restore new created user so when try to get the user that created in previous request API can retrieve any data
    @Test()
    public void testGetUser(ITestContext context)  {
        try {
            int id = (int) context.getSuite().getAttribute("user_id");
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(new File(System.getProperty("user.dir") + "/TestData/postBody.json"));
            String name = jsonNode.get("name").asText();
            Response response = UserEndPoints.getUser();
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("data.id", equalTo(userPayload.getId())) // Validate the user ID
                    .body("data.first_name", equalTo(name)) // Validate the first name
                    .body("data.last_name", is(notNullValue())) // Validate that last name is not null (based on API structure)
                    .body("data.email", is(notNullValue())); // Validate that email is not null (based on API structure)
        } catch (IOException e) {
            logger.error("Network error occurred: {}", e.getMessage());
        } catch (Exception e) {
            logger.debug("Failed to retrieve user as :{}", e.getMessage());
        }
    }


}

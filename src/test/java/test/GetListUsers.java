package test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static org.hamcrest.Matchers.*;

public class GetListUsers {
    public Logger logger = LogManager.getLogger(GetListUsers.class);

    @Test
    public void testGetListUsers() throws IOException {
        try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(System.getProperty("user.dir")+"/TestData/expectedResult.json"));
        int Expected_Page = jsonNode.get("Expected_Page").asInt();
        int Expected_Per_Page = jsonNode.get("Expected_Per_Page").asInt();


        Response response = UserEndPoints.getListUsers();
        response.then()
                .statusCode(200)
                .body("data.size()", greaterThan(0))
                .body("page", equalTo(Expected_Page))
                .body("per_page", equalTo(Expected_Per_Page))
                .body("total", greaterThan(0))
                .body("total_pages", greaterThan(0))
                .log().all();
        } catch (IOException e) {
            logger.error("Network error occurred: {}", e.getMessage());

        }catch (Exception e){

            logger.debug("Failed to create user as :{}", e.getMessage());
        }

    }

}

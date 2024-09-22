package test;

import endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.IOException;
import static org.hamcrest.Matchers.*;




public class DeleteUser {
    public Logger logger = LogManager.getLogger(DeleteUser.class);
    @Test
    public void testDeleteUser(ITestContext context)  {

        try {
            int id = (int) context.getSuite().getAttribute("user_id");
            Response response = UserEndPoints.deleteUser();
            response.then()
                    .statusCode(204)
                    .body(is(emptyOrNullString()))
                    .log().all();

        } catch (Exception e) {
            logger.debug("Failed to Delete user as :{}", e.getMessage());
        }
    }
}

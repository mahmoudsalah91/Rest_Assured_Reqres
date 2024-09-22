package endpoints;



import io.restassured.response.Response;
import payload.User;
import utilities.FileUtility;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


import static io.restassured.RestAssured.*;

public class UserEndPoints {

    public static int id;


    public static Response createUser() throws IOException {

        User userPayload = new User();

        Response response =given()
                .contentType("application/json")
                .body(FileUtility.readFile(System.getProperty("user.dir")+"/TestData/postBody.json"))

                .when().post(FileUtility.getURL().getString("Post_url"));
               id = response.getBody().jsonPath().getInt("id");
               userPayload.setId(id);
        System.out.println(id);

        return response;
    }

    public static Response getUser()
    {
        Response response =given()
                .pathParam("id",id)
                .when().get(FileUtility.getURL().getString("Get_user_url"));
        return response;
    }



    public static Response updateUser()
    {
        Response response =given()
                .contentType("application/json")
                .body(FileUtility.readFile(System.getProperty("user.dir") +"/TestData/updateBody.json"))
                .pathParam("id",id)
                .when().put(FileUtility.getURL().getString("Update_url"));

        return response;
    }

    public static Response deleteUser()
    {
        Response response =given()
                .contentType("application/json")
                .pathParam("id",id)
                .when().delete(FileUtility.getURL().getString("Delete_url"));

        return response;
    }

    public static Response getListUsers() throws IOException {


        Response response =given()
                .contentType("application/json")
                .when().get(FileUtility.getURL().getString("Get_list_url"));

        return response;
    }



}

package utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ResourceBundle;

public class FileUtility {

    public static String readFile(String filePath) {
        File file = new File(filePath);
        String JsonData = null;
        try {
            FileReader reader = new FileReader(file);
            JSONTokener jsonTokener = new JSONTokener(reader);
            JSONObject data = new JSONObject(jsonTokener);
            JsonData = data.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load configuration values from the file");
        }

        return JsonData;
    }

    public static ResourceBundle getURL (){
        ResourceBundle routes=ResourceBundle.getBundle("routes");
        return routes;
    }

}

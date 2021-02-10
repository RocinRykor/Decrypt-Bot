package studio.rrprojects.decryptbot.utils;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {

    public static JSONObject LoadJsonFromFile(File file) {
        JSONObject mainObj;

        try {
            FileReader reader = new FileReader(file);
            JsonValue tmp = Json.parse(reader);
            mainObj = new JSONObject(tmp.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return mainObj;
    }

}

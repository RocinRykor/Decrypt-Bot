package studio.rrprojects.decryptbot.utils;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.PrettyPrint;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static void WriteJsonToFile(File inputFile, JSONObject inputJson) {
        String jsonString = inputJson.toString();
        JsonValue writeJson = Json.parse(jsonString);
        FileWriter writer = null;

        try {
            writer = new FileWriter(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writeJson.writeTo(writer, PrettyPrint.PRETTY_PRINT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert writer != null;
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

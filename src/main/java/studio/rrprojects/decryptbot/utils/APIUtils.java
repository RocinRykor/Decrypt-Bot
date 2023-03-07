package studio.rrprojects.decryptbot.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class APIUtils {
    public HttpResponse POSTtoURL(String URL, JSONObject payload){
        HttpClient httpClient    = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(URL);
        post.setEntity(new StringEntity(payload.toString(), ContentType.APPLICATION_JSON));
        try {
            return httpClient.execute(post);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject ConvertResponseToJSON(HttpResponse response) {
        String jsonString = null;

        try {
            jsonString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JSONObject(jsonString);
    }
}

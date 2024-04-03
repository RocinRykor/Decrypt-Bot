package studio.rrprojects.decryptbot.commands.basic;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.util_library.DebugUtils;

import java.io.IOException;

public class SiteConnect extends studio.rrprojects.decryptbot.commands.Command {

    /*
    This is going to be a testing ground function for the new SWS site API
    The idea will be that instead of housing the calculations for game-related commands such as generators (Matrix Host or Magical Group),
    we will connect to the site via API, pass it the parameters and let it do the calculations and return the result
     */

    @Override
    public String getName() {
        return "Site";
    }

    @Override
    public String getAlias() {
        return "SWS";
    }

    @Override
    public String getHelpDescription() {
        return "A quick test of connecting to the Sixth-World Sprawl Site via the API and getting a simple return";
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        HttpClient httpClient    = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("http://localhost:5001/api/bot/test");
        JSONObject payload = new JSONObject();
        payload.put("key", "Hello World!");

        System.out.println(payload);
        post.setEntity(new StringEntity(payload.toString(), ContentType.APPLICATION_JSON));
        try {
            HttpResponse response = httpClient.execute(post);

            String jsonString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(jsonString);

            DebugUtils.UnknownMsg("JSON TEST: " + jsonObject);

            SendBasicMessage(jsonObject.getString("new_value "), cmd.getEvent().getChannel());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
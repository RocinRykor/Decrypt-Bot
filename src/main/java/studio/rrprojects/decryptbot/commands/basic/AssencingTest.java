package studio.rrprojects.decryptbot.commands.basic;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.APIUtils;

import java.util.ArrayList;

public class AssencingTest extends studio.rrprojects.decryptbot.commands.Command {
    @Override
    public String getName() {
        return "Assencing";
    }

    @Override
    public String getAlias() {
        return "AT";
    }

    @Override
    public String getHelpDescription() {
        return null;
    }

    /*
    Performs an assencing success test, along with a supplementary aura reading test
    Returns the roll results as well as the relevant info from the Assencing Table (CORE p.172)

    Command format will include 1-3 integers in the following order

    Assencing Test Dice Pool - Required
    Target Number - Optional - Defaults to 4
    Aura Reading Dice Pool - Optional - Defaults to 0
     */

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        ArrayList<String> params = cmd.getListParameters();
        int assencingTestDicePool = 1;
        int targetNumber = 4;
        int auraReadingDicePool = 0;

        if (params.size() > 2) {
            auraReadingDicePool = Integer.parseInt(params.get(2));
        }
        if (params.size() > 1) {
            targetNumber = Integer.parseInt(params.get(1));
        }

        if (params.size() > 0) {
            assencingTestDicePool = Integer.parseInt(params.get(0));
        }

        System.out.println("Assencing Dice Pool: " + assencingTestDicePool);
        System.out.println("Target Number: " + targetNumber);
        System.out.println("Aura Dice Pool: " + auraReadingDicePool);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dice_pool", assencingTestDicePool);
        jsonObject.put("target_number", targetNumber);
        jsonObject.put("aura_reading", auraReadingDicePool);

        APIUtils apiUtils = new APIUtils();
        String URL = "https://sixthworldsprawl.com/api/bot/assencing_test";

        HttpResponse response = apiUtils.POSTtoURL(URL, jsonObject);
        JSONObject jsonResponse = apiUtils.ConvertResponseToJSON(response);

        // Going to have to recreate the RollFormatter for this since it is using a different layout of data
        int totalHits = jsonResponse.getInt("Total Hits");

        String title = "Comprehensive Assencing Test:\n";
        String totalHitsString = "<Total Hits : " + totalHits + " >\n";
        String targetString = "Target Number: " + targetNumber + "\n\n";

        String tableBlock = failureBlock();
        if (totalHits > 0) {
            tableBlock = convertTableToBlock(jsonResponse.getJSONObject("Information"));
        }

        String resultsAssencing = convertTestToBlock(jsonResponse.getJSONObject("Assencing Test"), "Assencing Test");

        String resultsAuraReading = "";
        if (auraReadingDicePool > 0) {
            resultsAuraReading = convertTestToBlock(jsonResponse.getJSONObject("Aura Reading"), "Aura Reading Supplementary Test");
        }

        String outputMessage = title + totalHitsString + targetString + tableBlock + resultsAssencing + resultsAuraReading;

        SendBlockMessage(outputMessage, "md", cmd.getEvent().getChannel());

    }

    private String failureBlock() {
        return "=========================\n" +
                "NO HITS: TEST FAILURE!\n" +
                "NO INFORMATION GAINED\n" +
                "=========================\n";
    }

    private String convertTableToBlock(JSONObject information) {
        String output = "=========================\n\n";
        for (String category : information.keySet()) {
            output += "<" + category + ": " + information.get(category) + " >\n\n";
        }
        output += "=========================\n";
        return output;
    }

    private String convertTestToBlock(JSONObject jsonTest, String title) {
        return  title + " Results: \n" +
                "<Hits: " + jsonTest.get("Results")+ " >\n" +
                "> Results: " + jsonTest.getJSONArray("Rolls").toString() + "\n" +
                "=========================\n" +
                "\n";
    }
}


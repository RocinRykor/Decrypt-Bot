package studio.rrprojects.decryptbot.commands.basic;

import org.json.JSONArray;
import org.json.JSONObject;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.util_library.FileUtil;
import studio.rrprojects.util_library.JSONUtil;
import studio.rrprojects.util_library.MathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class RuleCommand extends Command {

    private final JSONObject rulesObj;
    private final ArrayList<RuleObject> listRules;

    @Override
    public String getName() {
        return "Rule";
    }

    @Override
    public String getAlias() {
        return "rules";
    }

    @Override
    public String getHelpDescription() {
        return "Allows you to search for and bring up rules for SR3e";
    }

    public RuleCommand() {
        String filePath = FileConstants.JSON_DIR + "rules_repo.json";
        File jsonFile = FileUtil.loadFileFromPath(filePath);
        rulesObj = JSONUtil.loadJsonFromFile(jsonFile);
        listRules = ConvertJsonToArrayList();
    }

    private ArrayList<RuleObject> ConvertJsonToArrayList() {
        ArrayList<RuleObject> tmp = new ArrayList<>();

        for (Iterator<String> it = rulesObj.keys(); it.hasNext(); ) {
            String ruleTitle = it.next();
            tmp.add(new RuleObject(ruleTitle, rulesObj.getJSONObject(ruleTitle)));
        }

        return tmp;
    }

    @Override
    public void executeMain(CommandContainer cmd) {

        int listSize = listRules.size();
        int randomNum = MathUtil.getRandomRange(0, listSize - 1);

        RuleObject rule = listRules.get(randomNum); //Get Random

        assert rule != null;
        String message = "Title: " + rule.title + "\n" +
                "Source: " + rule.source +"\n" +
                "Description: " + rule.description;

        SendBasicMessage(message, event.getChannel());
    }

    private class RuleObject {

        private final String title;
        private final String source;
        private final String description;
        private final ArrayList<String> listKeywords;

        public RuleObject(String title, JSONObject contents) {
            this.title = title;
            this.source = contents.getString("source");
            this.description = contents.getString("description");
            this.listKeywords = ProcessArray(contents.getJSONArray("keywords"));
        }

        private ArrayList<String> ProcessArray(JSONArray keywords) {
            ArrayList<String> tmp = new ArrayList<>();
            for (Object keyword : keywords) {
                tmp.add(keyword.toString());
            }

            return tmp;
        }
    }
}

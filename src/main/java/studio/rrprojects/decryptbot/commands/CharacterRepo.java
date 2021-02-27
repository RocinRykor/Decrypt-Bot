package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.utils.MessageUtils;
import studio.rrprojects.util_library.FileUtil;
import studio.rrprojects.util_library.JSONUtil;
import studio.rrprojects.util_library.MathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class CharacterRepo extends Command {
    private JSONObject characterJson;
    private ArrayList<String> characterNameArray;

    @Override
    public String getName() {
        return "Character";
    }

    @Override
    public String getAlias() {
        return "Repo";
    }

    @Override
    public String getHelpDescription() {
        return "Grabs information about a selected Shadowrun character";
    }

    @Override
    public void executeMain(CommandContainer input, MessageReceivedEvent event) {
        String message = GetRandomCharacter();

        MessageUtils.SendMessage(MessageUtils.BlockText(message, null), event.getChannel());
    }

    private String GetRandomCharacter() {
        int arraySize = characterNameArray.size() - 1;
        String characterName = characterNameArray.get(MathUtil.getRandomRange(0, arraySize));

        JSONObject selectedCharacter = characterJson.getJSONObject(characterName);

        String characterCreator = selectedCharacter.getString("creator");
        String characterDescription = selectedCharacter.getString("description");


        return String.format("Character: %s\n\n" +
                "Created By: %s\n\n" +
                "Description: %s",
                characterName, characterCreator, characterDescription);
    }

    @Override
    public void Initialize() {
        String jsonDirString = MainController.getMainDir() + "JSON" + File.separator;
        String jsonFilePath = jsonDirString + "character_repo.json";

        characterJson = JSONUtil.loadJsonFromFile(FileUtil.loadFileFromPath(jsonFilePath));

        assert characterJson != null;

        Map<String, Object> map = characterJson.toMap();

        characterNameArray = new ArrayList<>();

        map.forEach((key, value) -> {
            characterNameArray.add(key);
        });
    }
}

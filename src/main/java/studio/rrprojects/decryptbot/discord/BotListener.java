package studio.rrprojects.decryptbot.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.commands.CommandController;

import java.util.HashMap;
import java.util.Map;

public class BotListener extends ListenerAdapter {
    private final MainController mainController;
    private CommandController commandController;
    private HashMap<String, String> prefixTable = new HashMap<>();

    public BotListener(MainController mainController) {
        this.mainController = mainController;
        InitializePrefixTable();
    }

    private void InitializePrefixTable() {
        prefixTable.put("d.", "");
        prefixTable.put("D.", "");
        prefixTable.put("Decrypt, ", "");
        prefixTable.put("!", "");
        prefixTable.put("&", "");
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

//        DebugUtils.UnknownMsg("RECEIVED A MESSAGE!");

        String messageRaw = event.getMessage().getContentRaw();

        //Check for Prefix
        for (Map.Entry<String, String> prefix : prefixTable.entrySet()) {
            if (messageRaw.startsWith(prefix.getKey())) {
                String beheaded = messageRaw.replace(prefix.getKey(), prefix.getValue());

                commandController.ProcessInput(beheaded, event);
            }
        }

    }

    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
//        DebugUtils.ProgressNormalMsg("BOT READY EVENT RECEIVED!");
        mainController.ReadyEvent();
    }
    public void setCommandController(CommandController commandController) {
        this.commandController = commandController;
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public void setTestingMode() {
        prefixTable.clear();
        prefixTable.put("~", "");
    }

}

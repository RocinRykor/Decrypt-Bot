package studio.rrprojects.decryptbot.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import studio.rrprojects.decryptbot.commands.CommandController;

import java.util.HashMap;
import java.util.Map;

public class BotListener extends ListenerAdapter {
    private final CommandController commandController;
    private HashMap<String, String> prefixTable;

    public BotListener(CommandController commandController) {
        this.commandController = commandController;
        InitializePrefixTable();
    }

    private void InitializePrefixTable() {
        prefixTable = new HashMap<>();
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

        System.out.println("RECEIVED A MESSAGE!");

        String messageRaw = event.getMessage().getContentRaw();

        //Check for Prefix
        for (Map.Entry<String, String> prefix: prefixTable.entrySet()) {
            if (messageRaw.startsWith(prefix.getKey())) {
                commandController.ProcessInput(messageRaw.replace(prefix.getKey(), prefix.getValue()), event);
            }
        }

    }

}

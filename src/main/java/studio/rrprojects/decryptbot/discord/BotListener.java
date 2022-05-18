package studio.rrprojects.decryptbot.discord;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.commands.CommandController;
import studio.rrprojects.util_library.DebugUtils;

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

        DebugUtils.UnknownMsg("RECEIVED A MESSAGE!");

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
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        DebugUtils.ProgressNormalMsg("BOT READY EVENT RECIEVED!");
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

    /* - Slash Commands (Something's Not Working Here)

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        DebugUtils.UnknownMsg("SLASH COMMAND RECEIVED!");

        if (event.getName().equals("hello")) {
            event.reply("Click the button to say hello")
                    .addActionRow(
                            Button.primary("hello", "Click Me"), // Button with only a label
                            Button.success("emoji", Emoji.fromMarkdown("<:minn:245267426227388416>"))) // Button with only an emoji
                    .queue();
        } else if (event.getName().equals("info")) {
            event.reply("Click the buttons for more info")
                    .addActionRow( // link buttons don't send events, they just open a link in the browser when clicked
                            Button.link("https://github.com/DV8FromTheWorld/JDA", "GitHub")
                                    .withEmoji(Emoji.fromMarkdown("<:github:849286315580719104>")), // Link Button with label and emoji
                            Button.link("https://ci.dv8tion.net/job/JDA/javadoc/", "Javadocs")) // Link Button with only a label
                    .queue();
        } else if (event.getName().equals("ping")) return; // make sure we handle the right command
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                ).queue(); // Queue both reply and edit

    }

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        if (event.getComponentId().equals("hello")) {
            event.reply("Hello :)").queue(); // send a message in the channel
        } else if (event.getComponentId().equals("emoji")) {
            event.editMessage("That button didn't say click me").queue(); // update the message
        }
    }

     */
}

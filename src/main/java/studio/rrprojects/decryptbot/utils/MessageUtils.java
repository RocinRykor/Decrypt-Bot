package studio.rrprojects.decryptbot.utils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class MessageUtils {
    public static void SendMessage(String message, MessageChannel destination) {
        destination.sendMessage(message).queue();
    }

    public static String BlockText(String input, String markdownType) {
        String blockType = "";
        if (markdownType != null) {
            blockType = markdownType;
        }

        return "```" + blockType + "\n" + input
                + "\n```";
    }

    public static void SendEmbedMessage(MessageEmbed message, MessageChannel destination) {
        destination.sendMessage(message).queue();
    }
}

package studio.rrprojects.decryptbot.utils;

import net.dv8tion.jda.api.entities.MessageChannel;

public class MessageUtils {
    public static void SendMessage(String message, MessageChannel destination) {
        destination.sendMessage(message).queue();
    }
}

package studio.rrprojects.decryptbot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import studio.rrprojects.decryptbot.commands.basic.Delete;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;

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

    public static void LogEventPermission(CommandContainer cmd, String commandName) {
        User owner = cmd.getEvent().getJDA().getGuilds().get(0).getOwner().getUser();
        User author = cmd.getEvent().getAuthor();
        String logMessage = author.getName() + " has attempted to use the \"" + commandName + "\" command, without the proper permissions.";

        owner.openPrivateChannel().complete().sendMessage(logMessage).complete();
    }
}

package studio.rrprojects.decryptbot.utils;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.constants.MarkdownStyles;
import studio.rrprojects.util_library.DebugUtils;

import java.util.ArrayList;

public class MessageUtils {
    public static void SendMessage(String message, MessageChannel destination) {
        final int messageLimit = 2000;

        ArrayList<String> messageList = BreakDownMessage(message, messageLimit);

        for (String messageChunk : messageList) {
            destination.sendMessage(messageChunk).queue();
        }

    }

    public static void SendBlockMessage(String message, String markdownType, MessageChannel destination) {
        final int messageLimit = 1950;

        ArrayList<String> messageList = BreakDownMessage(message, messageLimit);

        for (String messageChunk : messageList) {
            destination.sendMessage(BlockText(messageChunk, markdownType)).queue();
        }

    }

    private static ArrayList<String> BreakDownMessage(String message, int messageLimit) {
        DebugUtils.UnknownMsg("MESSAGE TOO LONG -> Breaking Down!");

        ArrayList<String> messageChunks = new ArrayList<>();
        String remainingMessage = message;
        DebugUtils.VaraibleMsg("Remaining Message: " + remainingMessage);

        while (remainingMessage.length() > 0) {

            if (remainingMessage.length() >= messageLimit) {
                String rawChunk = remainingMessage.substring(0, messageLimit);
                DebugUtils.VaraibleMsg("Raw Chunk: " + rawChunk);

                //First try line break
                int lastIndex = rawChunk.lastIndexOf("\n");

                //Try against punctuation
                if (lastIndex == -1) {
                    lastIndex = rawChunk.lastIndexOf(".");
                }

                //Try against space character
                if (lastIndex == -1) {
                    lastIndex = rawChunk.lastIndexOf(" ");
                }


                //All else fails, use message limit
                if (lastIndex == -1 ) {
                    lastIndex = messageLimit;
                }

                //Note to Self: TODO May try a switch case

                String refinedChunk = rawChunk.substring(0, lastIndex + 1);
                DebugUtils.VaraibleMsg("Refined Chunk: " + refinedChunk);

                messageChunks.add(refinedChunk);
                remainingMessage = remainingMessage.substring(refinedChunk.length()).trim();
            } else {
                messageChunks.add(remainingMessage);
                remainingMessage = "";
            }

        }

        DebugUtils.VaraibleMsg("Total Number of Messages: " + messageChunks.size());
        return messageChunks;
    }

    public static String BlockText(String input, String markdownType) {

        if (markdownType == null) {
            markdownType = MarkdownStyles.NONE;
        }

        return "```" + markdownType + "\n" + input
                + "\n```";
    }

    public static void SendEmbedMessage(MessageEmbed message, MessageChannel destination) {
        destination.sendMessageEmbeds(message).queue();
    }

    public static void LogEventPermission(CommandContainer cmd, String commandName) {
        User owner = cmd.getEvent().getJDA().getGuilds().get(0).getOwner().getUser();
        User author = cmd.getEvent().getAuthor();
        String logMessage = author.getName() + " has attempted to use the \"" + commandName + "\" command, without the proper permissions.";

        owner.openPrivateChannel().complete().sendMessage(logMessage).complete();
    }
}

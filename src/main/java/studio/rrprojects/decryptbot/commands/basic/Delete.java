package studio.rrprojects.decryptbot.commands.basic;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.MessageHistory;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.DiscordUtils;

import java.util.ArrayList;

public class Delete extends Command {
    @Override
    public String getName() {
        return "Delete";
    }

    @Override
    public String getAlias() {
        return "Clear";
    }

    @Override
    public String getHelpDescription() {
        return "Deletes a selected amount of messages in the channel - Note: Requires Admin role to perform";
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        if (DiscordUtils.CheckIfAdmin(cmd)) {
            DeleteMessages(cmd);
        } else {
            ErrorMessage(cmd);
        }
    }

    private void DeleteMessages(CommandContainer cmd) {
        MessageChannel channel = cmd.getEvent().getChannel();
        int deleteAmount = GetDeleteAmount(cmd.getListParameters().get(0));

        if (!(cmd.getEvent().getChannel() instanceof TextChannel)) {
            System.out.println("Delete ERROR: Destination Channel is not a Text Channel - CANNOT DELETE!");
            return;
        }

        TextChannel destination = (TextChannel) cmd.getEvent().getChannel();

        //Start by placing a message in the selected channel as a starting point and deleting it
        Message startingMessage = destination.sendMessage("Beginning Deletion: Please Stand By!").complete();
        Message currentMessage = startingMessage;

        ArrayList<Message> messageList = new ArrayList<>();
        MessageHistory messageHistory = startingMessage.getChannel().getHistoryBefore(currentMessage, 10).complete();

        //Begin Deletion as a new thread
        while (deleteAmount > 0) {

            if (deleteAmount >= 100) {
                messageHistory = currentMessage.getChannel().getHistoryBefore(currentMessage, 100).complete();
                if (messageHistory.size() < 100) {
                    deleteAmount = 0;
                } else {
                    deleteAmount -= messageHistory.size();
                }
            } else {
                messageHistory = currentMessage.getChannel().getHistoryBefore(currentMessage, deleteAmount).complete();
                if (messageHistory.size() < deleteAmount) {
                    deleteAmount = 0;
                } else {
                    deleteAmount -= messageHistory.size();
                }
            }

            messageList.addAll(messageHistory.getRetrievedHistory());
            currentMessage = messageList.get(messageList.size() - 1);
        }

        System.out.println("Number of messages collected: " + messageList.size());

        messageList.removeIf(message -> message.isPinned() || !message.getAttachments().isEmpty());

        startingMessage.getChannel().purgeMessages(messageList);

        System.out.println("Deletion Complete!");

        startingMessage.delete().complete();
    }

    // UTILS
    private int GetDeleteAmount(String input) {
        int defaultAmount = 10;

        if (input != null) {
            if(input.equalsIgnoreCase("all")) {
                return 9999;
            }

            try {
                Integer.parseInt(input);
                return Integer.parseInt(input);
            } catch (Exception e) {
                return defaultAmount;
            }
        } else {
            return defaultAmount;
        }

    }
}

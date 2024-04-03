package studio.rrprojects.decryptbot.commands.basic;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;

import studio.rrprojects.decryptbot.utils.DiscordUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Flood extends Command {
    @Override
    public String getName() {
        return "Flood";
    }

    @Override
    public String getAlias() {
        return "flood";
    }

    @Override
    public String getHelpDescription() {
        return "Generates a number of messages in a text channel. Note: This is an admin command only for bottesting purposes. Misuse of this command will be punished.";
    }

    private int maxFloodAmount = 100;
    private int defaultAmount = 10;

    @Override
    public void executeMain(CommandContainer cmd) {
        if (DiscordUtils.CheckIfAdmin(cmd)) {
            GenerateMessages(cmd);
        } else {
            ErrorMessage(cmd);
        }
    }

    private void GenerateMessages(CommandContainer cmd) {
        MessageChannel channel = cmd.getEvent().getChannel();
        int floodAmount = GetFloodAmount(cmd.getListParameters().get(0));

        if (!(cmd.getEvent().getChannel() instanceof TextChannel)) {
            System.out.println("Delete ERROR: Destination Channel is not a Text Channel - CANNOT DELETE!");
            return;
        }

        long startingTime = System.currentTimeMillis();
        new Thread(() -> {

            for (int i = 1; i <= floodAmount; i++) {
                int remainingPosts = floodAmount - i;

                String message = "Message #" + i + "/" + floodAmount + "\n";

                long currentTime = System.currentTimeMillis();
                long estimatedFinishTime = currentTime + ((long) remainingPosts * 5000); //TODO Fix Magic Number

                message += "Estimate Time of Completion: " + ConvertLongToDate(estimatedFinishTime);

                channel.sendMessage(message).complete();

                try {
                    sleep(5000); //TODO Fix Magic Number
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private String ConvertLongToDate(long time) {

        // Creating date format
        DateFormat simple = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(time);

        // Formatting Date according to the
        // given format
        return simple.format(result);
    }

    private int GetFloodAmount(String input) {

        if (input != null) {
            if(input.equalsIgnoreCase("all")) {
                return maxFloodAmount;
            }

            int parsedAmount =  defaultAmount;

            try {
                parsedAmount = Integer.parseInt(input);
            } catch (Exception e) {
                return defaultAmount;
            }

            return Math.min(parsedAmount, maxFloodAmount);

        } else {
            return defaultAmount;
        }
    }
}

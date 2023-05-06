package studio.rrprojects.decryptbot.commands.basic;

import net.dv8tion.jda.api.entities.MessageChannel;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.controllers.ChoreListController;
import studio.rrprojects.decryptbot.utils.MessageUtils;

public class ChoreCommand extends Command {
    private ChoreListController choreListController;

    @Override
    public String getName() {
        return "Chore";
    }

    @Override
    public String getAlias() {
        return "Task";
    }

    @Override
    public String getHelpDescription() {
        return "NONE";
    }

    @Override
    public void Initialize() {
        super.Initialize();

        choreListController = new ChoreListController();
        choreListController.addChore("Take out trash");
        choreListController.addChore("Wash Dishes");
        choreListController.addChore("Laundry");
        choreListController.addChore("Clean the counters");
    }

    public void executeMain(CommandContainer cmd) {
        String key = cmd.getPrimaryParameter();

        if (key.equalsIgnoreCase("list")) {
            listChores(cmd.getEvent().getChannel());
        } else if (key.equalsIgnoreCase("add")) {
            choreListController.addChore(cmd.getNotes());
        } else if (key.equalsIgnoreCase("remove")) {
            choreListController.removeChore(cmd.getNotes());
        } else if (key.equalsIgnoreCase("roll")) {
            randomChore(cmd.getEvent().getChannel());
        } else if (key.equalsIgnoreCase("finish")) {
            completeChore(cmd.getNotes(), cmd.getEvent().getChannel());
        }
    }

    private void completeChore(String chore, MessageChannel channel) {
        if (choreListController.completeChore(chore)) {
            String message = "Success! \"" + chore + "\" has been completed!";
            SendBasicMessage(message, channel);
        } else {
            SendBasicMessage("Looks like something went wrong, check the chore list with \"!chore list\"", channel);
        }
    }

    private void randomChore(MessageChannel channel) {
        String chore = choreListController.getRandomChoreAndStart();
        String message;

        if (chore == null) {
            message = "Error: Looks like you dont have any chores to start from";
        } else {
            message = "Picking a random chore!\n\n\"" +
                    "Your new chore is: " + chore + "\n\n" +
                    "Chore has been added to the start list";
        }

        MessageUtils.SendMessage(message, channel);
    }

    private void listChores(MessageChannel channel) {
        System.out.println(choreListController.getChoreMap());

        String completed = "__Completed Tasks__\n";
        for (String chore: choreListController.getChoresByStatus(2)) {
            completed += "~~" + chore + "~~\n";
        }
        String started = "__Started Tasks__\n";
        for (String chore: choreListController.getChoresByStatus(1)) {
            started += "_" + chore + "_\n";
        }
        String notStarted = "__Remaining Tasks__\n";
        for (String chore: choreListController.getChoresByStatus(0)) {
            notStarted += chore + "\n";
        }

        String message = completed + "\n" + started + "\n" + notStarted;

        System.out.println(message);

        SendBasicMessage(message, channel);
    }
}

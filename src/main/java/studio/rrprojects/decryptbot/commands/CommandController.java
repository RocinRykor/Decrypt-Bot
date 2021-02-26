package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.commands.basic.PingCommand;
import studio.rrprojects.decryptbot.commands.basic.RollCommand;
import studio.rrprojects.decryptbot.commands.basic.RuleCommand;

import java.util.ArrayList;

public class CommandController {
    private final MainController mainController;
    private ArrayList<Command> basicCommands;

    public CommandController(MainController mainController) {
        this.mainController = mainController;

        InitializeCommands();

    }

    private void InitializeCommands() {
        System.out.println("COMMAND CONTROLLER: LOADING COMMANDS");
        basicCommands = new ArrayList<>();
        basicCommands.add(new PingCommand());
        basicCommands.add(new RollCommand());
        basicCommands.add(new RuleCommand());
        basicCommands.add(new CharacterRepo());

        for (Command command : basicCommands) {
            command.Initialize();
        }

        System.out.println("COMMAND CONTROLLER: " + basicCommands.size() + " COMMANDS LOADED SUCCESSFULLY");
    }

    public MainController getMainController() {
        return mainController;
    }

    public void ProcessInput(String input, MessageReceivedEvent event) {
        CommandContainer cmd = new CommandContainer(input);

        for (Command command: basicCommands) {
            if (cmd.primaryCommand.equalsIgnoreCase(command.getName())) {
                command.executeMain(cmd, event);
            }
        }
    }

}

package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.basic.*;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.commands.response.Ping;
import studio.rrprojects.util_library.DebugUtils;

import java.util.ArrayList;

public class CommandController {
    private final ArrayList<Command> commandList = new ArrayList<>();

    public CommandController() {
        InitializeCommands();
    }

    private void InitializeCommands() {
        DebugUtils.ProgressNormalMsg("COMMAND CONTROLLER: LOADING COMMANDS");

        commandList.add(new Ping());
        commandList.add(new RollCommand());
        commandList.add(new Delete());
        commandList.add(new Flood());
        commandList.add(new GenerateGroup());
        commandList.add(new Audio());

        for (Command command : commandList) {
            command.Initialize();
        }

        DebugUtils.VaraibleMsg("Total Commands Loaded: " + commandList.size());
    }

    public void ProcessInput(String input, MessageReceivedEvent event) {
        CommandContainer cmd = new CommandContainer(input, event);

        for (Command command: commandList) {
            if (cmd.getPrimaryCommand().equalsIgnoreCase(command.getName()) || cmd.getPrimaryCommand().equalsIgnoreCase(command.getAlias())) {
                command.executeMain(cmd);
                return;
            }
        }
    }

}

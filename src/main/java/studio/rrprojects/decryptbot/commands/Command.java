package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;
import studio.rrprojects.util_library.DebugUtils;

import java.util.HashMap;

public abstract class Command {
    protected CommandContainer cmd;
    private CommandContainer commandContainer;
    private MessageReceivedEvent event;
    private HashMap<String, Runnable> subCommandMap = new HashMap<>();

    public abstract String getName();
    public abstract String getAlias();
    public abstract String getHelpDescription();

    public Command() {
        DebugUtils.CautionMsg("NEW COMMAND LOADED: " + getName());
    }

    public void executeMain(CommandContainer cmd) {
        commandContainer = cmd;
        event = cmd.getEvent();

        DebugUtils.CautionMsg("EXECUTING COMMAND: " + cmd.getPrimaryCommand());
        DebugUtils.CautionMsg("PARAMETERS: " + cmd.getListParameters().toString());
        DebugUtils.CautionMsg("ARGUMENTS: " + cmd.getListArgs().toString());
    };

    public void executeHelp() {
    };

    public void SendBasicMessage(String message, MessageChannel destination) {
        MessageUtils.SendMessage(message, destination);
    }

    public void SendBlockMessage(String message, String markdownType, MessageChannel destination) {
        MessageUtils.SendBlockMessage(message, markdownType, destination);
    }

    public void Initialize() {
    }

    public void LogPermission(CommandContainer cmd) {
        MessageUtils.LogEventPermission(cmd, getName());
    }

    public void ErrorMessage(CommandContainer cmd) {
        this.cmd.getEvent().getMessage().delete().queue();

        User author = this.cmd.getEvent().getAuthor();

        String errorMessage = "I'm sorry, you are not able to run that command.";

        System.out.println(errorMessage);

        author.openPrivateChannel().complete().sendMessage(errorMessage).complete();

        LogPermission(this.cmd);
    }

    public CommandContainer getCommandContainer() {
        return commandContainer;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public CommandContainer getCmd() {
        return cmd;
    }

    public HashMap<String, Runnable> getSubCommandMap() {
        return subCommandMap;
    }

    protected boolean subCommandMapContains(String input) {
        DebugUtils.UnknownMsg("CHECKING SUBCOMMANDS: " + input);
        DebugUtils.VariableMsg("SUBCOMMAND SIZE: " + subCommandMap.size());

        for (String key: subCommandMap.keySet()) {
            if (key.equalsIgnoreCase(input)) {
                DebugUtils.VariableMsg("MATCH FOUND: " + key);
                return true;}
        }
        return false;
    }

    protected Runnable searchSubCommands(String input) {
        for (String key: subCommandMap.keySet()) {
            if (key.equalsIgnoreCase(input)) {return subCommandMap.get(key);}
        }
        return null;
    }
}

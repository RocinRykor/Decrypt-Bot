package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;
import studio.rrprojects.util_library.DebugUtils;

public abstract class Command {
    protected CommandContainer cmd;
    private CommandContainer commandContainer;
    private MessageReceivedEvent event;

    public abstract String getName();
    public abstract String getAlias();
    public abstract String getHelpDescription();

    public Command() {
        DebugUtils.CautionMsg("NEW COMMAND LOADED: " + getName());
    }

    public void executeMain(CommandContainer cmd) {
        commandContainer = cmd;
        event = cmd.getEvent();
    };
    public void executeHelp() { };

    public void SendBasicMessage(String message, MessageChannel destination) {
        MessageUtils.SendMessage(message, destination);
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
}

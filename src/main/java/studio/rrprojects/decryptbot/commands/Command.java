package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;

public abstract class Command {
    protected CommandContainer cmd;
    protected MessageReceivedEvent event;

    public abstract String getName();
    public abstract String getAlias();
    public abstract String getHelpDescription();

    public void executeMain(CommandContainer cmd) {};
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
}

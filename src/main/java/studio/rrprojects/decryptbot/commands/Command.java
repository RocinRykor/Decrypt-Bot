package studio.rrprojects.decryptbot.commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.utils.MessageUtils;

public abstract class Command {
    protected CommandContainer cmd;
    protected MessageReceivedEvent event;

    public abstract String getName();
    public abstract String getAlias();
    public abstract String getHelpDescription();

    public void executeMain(CommandContainer input, MessageReceivedEvent event) {};
    public void executeHelp() {};
    public void SendMessage(String message, MessageChannel destination) {
        MessageUtils.SendMessage(message, destination);
    }

    public void Initialize() {
    }
}

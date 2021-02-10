package studio.rrprojects.decryptbot.commands.basic;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.CommandContainer;

public class PingCommand extends Command {
    @Override
    public String getName() {
        return "Ping";
    }

    @Override
    public String getAlias() {
        return "P";
    }

    @Override
    public String getHelpDescription() {
        return "Pings the bot and receives a message in response";
    }

    @Override
    public void executeMain(CommandContainer input, MessageReceivedEvent event) {
        SendMessage("Message received, chummer", event.getChannel());
    }
}

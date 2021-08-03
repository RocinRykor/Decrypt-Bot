package studio.rrprojects.decryptbot.commands.response;

import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.generators.magical_groups.MagicalGroup;

public class Ping extends ResponseCommand {
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
    public String getResponse() {
        return "Message Recieved, Chummer!";
    }
}

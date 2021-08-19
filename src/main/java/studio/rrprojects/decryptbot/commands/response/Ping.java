package studio.rrprojects.decryptbot.commands.response;

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
        return "Greetings, Chummer!";
    }
}

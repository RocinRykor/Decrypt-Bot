package studio.rrprojects.decryptbot.commands.response;

import studio.rrprojects.decryptbot.commands.container.CommandContainer;

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
        return "Message received, Chummer!";
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        testFunction(cmd);
    }

    @Override
    public void Initialize() {
        super.Initialize();
    }

    private void testFunction(CommandContainer cmd) {
    }

}

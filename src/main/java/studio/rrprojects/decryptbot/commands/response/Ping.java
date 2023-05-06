package studio.rrprojects.decryptbot.commands.response;

import net.dv8tion.jda.api.interactions.callbacks.IMessageEditCallback;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.controllers.ChoreListController;
import studio.rrprojects.decryptbot.utils.MessageUtils;

public class Ping extends ResponseCommand {

    private ChoreListController choreListController;

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

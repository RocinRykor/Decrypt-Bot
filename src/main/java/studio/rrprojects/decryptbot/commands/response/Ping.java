package studio.rrprojects.decryptbot.commands.response;

import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.util_library.DebugUtils;

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
        return "Message recieved, Chummer!";
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        if (super.subCommandMapContains(cmd.getPrimaryParameter())) {
            super.searchSubCommands(cmd.getPrimaryParameter()).run();
        }
    }

    @Override
    public void Initialize() {
        super.Initialize();
        getSubCommandMap().put("test", this::testFunction);
    }

    private void testFunction() {
        DebugUtils.CautionMsg("TEST SUCCESS!");
    }
}

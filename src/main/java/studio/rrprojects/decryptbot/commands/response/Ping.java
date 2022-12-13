package studio.rrprojects.decryptbot.commands.response;

import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.constants.MarkdownStyles;
import studio.rrprojects.decryptbot.generators.matrix.MatrixHost;
import studio.rrprojects.decryptbot.generators.matrix.SecuritySheaf;
import studio.rrprojects.decryptbot.rollers.LifeLeft;
import studio.rrprojects.decryptbot.utils.MessageUtils;
import studio.rrprojects.util_library.DebugUtils;

public class Ping extends ResponseCommand {

    private MatrixHost matrixHost;
    private SecuritySheaf securitySheaf;

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

        /*
        if (super.subCommandMapContains(cmd.getPrimaryParameter())) {
            super.searchSubCommands(cmd.getPrimaryParameter()).run();
        }
         */

        testFunction(cmd);
    }

    @Override
    public void Initialize() {
        super.Initialize();

        matrixHost = new MatrixHost();
        securitySheaf = new SecuritySheaf();
        
        getSubCommandMap().put("test", () -> testFunction(cmd));
    }

    private void testFunction(CommandContainer cmd) {

        //MessageUtils.SendMessage(matrixHost.GenerateHost("Easy"), cmd.getEvent().getChannel());
        //MessageUtils.SendMessage(matrixHost.GenerateHost("Medium"), cmd.getEvent().getChannel());
        //MessageUtils.SendMessage(matrixHost.GenerateHost("Hard"), cmd.getEvent().getChannel());
        //MessageUtils.SendMessage(matrixHost.GenerateHost("Unbreakable"), cmd.getEvent().getChannel());

        //securitySheaf.generateSheaf(1, 7, false);

        LifeLeft lifeLeft = new LifeLeft();
        lifeLeft.stressTest(10000);

        DebugUtils.CautionMsg("TEST SUCCESS!");
    }
}

package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.rollers.BasicRoller;
import studio.rrprojects.decryptbot.rollers.ShadowrunRoller;

public class RollCommand extends Command {
    private final ShadowrunRoller shadowrunRoller;

    @Override
    public String getName() {
        return "Roll";
    }

    @Override
    public String getAlias() {
        return "R";
    }

    @Override
    public String getHelpDescription() {
        return "Rolls a specified number of dice";
    }

    public RollCommand() {
        shadowrunRoller = new ShadowrunRoller();
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        //Check if rolling Shadowrun style or Basic Dice
        String primaryParameter = cmd.getListParameters().get(0);

        if (primaryParameter.toLowerCase().contains("d")) {
            new BasicRoller(cmd);
        } else {
            shadowrunRoller.Roll(cmd);
        }

    }
}

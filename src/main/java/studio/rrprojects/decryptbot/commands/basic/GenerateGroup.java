package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.generators.magical_groups.MagicalGroup;

public class GenerateGroup extends Command {
    @Override
    public String getName() {
        return "Group";
    }

    @Override
    public String getAlias() {
        return "MG";
    }

    @Override
    public String getHelpDescription() {
        return null;
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        System.out.println("GENERATING MAGICAL GROUP!");

        generateNewGroup();
    }

    private void generateNewGroup() {
        System.out.println("MAGICAL GROUP: GENERATING NEW GROUP");

        String message = " Hey Chummer, I did some digging on the matrix for magical groups. This is one I found and all the info I could dig up on them.\n" +
                "```\n";

        MagicalGroup magicalGroup = new MagicalGroup("Magical Group of Seattle");
        magicalGroup.generateRandomGroup();
        System.out.println(magicalGroup);

        message += magicalGroup.build() + "\n```";

        SendBasicMessage(message, getEvent().getChannel());
    }
}

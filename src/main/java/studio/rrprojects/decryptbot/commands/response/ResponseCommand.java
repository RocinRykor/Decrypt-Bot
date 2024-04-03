package studio.rrprojects.decryptbot.commands.response;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;

public abstract class ResponseCommand extends Command {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getHelpDescription() {
        return null;
    }

    public abstract String getResponse();

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);
        MessageChannel destination = cmd.getEvent().getChannel();
        MessageUtils.SendMessage(getResponse(), destination);
    }
}

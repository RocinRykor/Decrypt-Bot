package studio.rrprojects.decryptbot.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;

public class DiscordUtils {
    public static boolean CheckIfAdmin(CommandContainer cmd) {
        String rollName = "Admin";
        Role adminRole = cmd.getEvent().getGuild().getRolesByName(rollName, true).get(0);
        Member member = cmd.getEvent().getMember();

        return member.getRoles().contains(adminRole);
    }
}

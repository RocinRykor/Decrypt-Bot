package studio.rrprojects.decryptbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import studio.rrprojects.decryptbot.commands.CommandController;
import studio.rrprojects.decryptbot.config.ConfigController;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.decryptbot.discord.BotListener;
import studio.rrprojects.decryptbot.gui.ProgramAddGUI;
import studio.rrprojects.util_library.FileUtil;

import javax.security.auth.login.LoginException;
import java.io.File;

public class MainController {
    private final ConfigController configController;
    private final JDA jda;
    private final CommandController commandController;

    MainController() {
        System.out.println("MAIN CONTROLLER: STARTING!");

        //Create the Main Directory
        CreateMainDir();

        //Grab Configs
        configController = new ConfigController(this);

        //Launch JDA
        jda = StartJDA();

        //Initialize Commands
        commandController = new CommandController(this);

        //Start Listener
        BotListener botListener = new BotListener(commandController);
        jda.addEventListener(botListener);

        //ProgramAddGUI programAddGUI = new ProgramAddGUI("Decking Utility Add", this);
    }

    private void CreateMainDir() {
        FileUtil.CreateDir(FileConstants.HOME_DIR);
    }

    private JDA StartJDA() {

        try {
            return JDABuilder.createDefault(configController.getOption("botToken")).build();
        } catch (LoginException e) {
            e.printStackTrace();
            System.out.println("MAIN CONTROLLER: ERROR - UNABLE TO LOGIN, PLEASE CHECK CONFIG FILE");
            System.exit(0);
        }

        return null;
    }

    public JDA getJda() {
        return jda;
    }

}

package studio.rrprojects.decryptbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import studio.rrprojects.decryptbot.commands.CommandController;
import studio.rrprojects.decryptbot.config.ConfigController;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.decryptbot.discord.BotListener;
import studio.rrprojects.util_library.DebugUtils;
import studio.rrprojects.util_library.FileUtil;

import javax.security.auth.login.LoginException;

public class MainController {
    private final ConfigController configController;
    private final JDA jda;

    MainController() {
        DebugUtils.ProgressNormalMsg("MAIN CONTROLLER: STARTING!");

        //Create the Main Directory
        CreateMainDir();

        //Grab Configs
        configController = new ConfigController();

        //Launch JDA
        jda = StartJDA();

        //Initialize Commands
        CommandController commandController = new CommandController();

        //Start Listener
        BotListener botListener = new BotListener();
        boolean isTesting = Boolean.parseBoolean(configController.getOption("isTesting"));
        botListener.setTestingMode(isTesting);
        botListener.setCommandController(commandController);
        jda.addEventListener(botListener);
    }

    private void CreateMainDir() {
        FileUtil.CreateDir(FileConstants.HOME_DIR);
    }

    private JDA StartJDA() {
        try {
            return JDABuilder.createDefault(configController.getOption("botToken")).build();
        } catch (LoginException e) {
            e.printStackTrace();
            DebugUtils.ErrorMsg("MAIN CONTROLLER: ERROR - UNABLE TO LOGIN, PLEASE CHECK CONFIG FILE");
            System.exit(0);
        }

        return null;
    }

    public JDA getJda() {
        return jda;
    }

}

package studio.rrprojects.decryptbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import studio.rrprojects.decryptbot.commands.CommandController;
import studio.rrprojects.decryptbot.config.ConfigController;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.decryptbot.discord.BotListener;
import studio.rrprojects.util_library.DebugUtils;
import studio.rrprojects.util_library.FileUtil;

public class MainController {
    private final ConfigController configController;
    private final JDA jda;
    private final CommandController commandController;

    MainController() {
        DebugUtils.ProgressNormalMsg("MAIN CONTROLLER: STARTING!");

        //Create the Main Directory
        CreateMainDir();

        //Grab Configs
        configController = new ConfigController();

        //Launch JDA
        jda = StartJDA();

        //Initialize Commands
        commandController = new CommandController();

        //Start Listener
        BotListener botListener = new BotListener(this);
        boolean isTesting = Boolean.parseBoolean(configController.getOption("isTesting"));

        if (isTesting) {
            botListener.setTestingMode();
        }

        botListener.setCommandController(commandController);
        jda.addEventListener(botListener);

        //RulesGui rulesGui = new RulesGui("Rules", this);

    }

    private void CreateMainDir() {
        FileUtil.CreateDir(FileConstants.HOME_DIR);
    }

    private JDA StartJDA() {
        return JDABuilder.createDefault(configController.getOption("botToken")).build();

    }

    public JDA getJda() {
        return jda;
    }

    public void ReadyEvent() {
    }
}

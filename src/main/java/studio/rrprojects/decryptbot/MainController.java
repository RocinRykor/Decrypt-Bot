package studio.rrprojects.decryptbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import studio.rrprojects.decryptbot.audio.AudioController;
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
    private final AudioController audioController;
    private final CommandController commandController;

    MainController() {
        DebugUtils.ProgressNormalMsg("MAIN CONTROLLER: STARTING!");

        //Create the Main Directory
        CreateMainDir();

        //Grab Configs
        configController = new ConfigController();

        //Launch JDA
        jda = StartJDA();


        //Initialize AudioController
        audioController = new AudioController();

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

        //Force A Ready Event
        ReadyEvent();

        /*
        Slash Commands

        jda.upsertCommand("ping", "Calculate ping of the bot").queue(); // This can take up to 1 hour to show up in the client
        jda.upsertCommand("hello", "testing slash commands").queue(); // This can take up to 1 hour to show up in the client
        jda.upsertCommand("info", "testing more slash commands").queue(); // This can take up to 1 hour to show up in the client
        jda.updateCommands();
         */

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

    public void ReadyEvent() {
        audioController.setGuild(jda.getGuilds().get(0));
        commandController.ProcessAudioController(audioController);
    }
}

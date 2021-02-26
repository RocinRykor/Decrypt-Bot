package studio.rrprojects.decryptbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import studio.rrprojects.decryptbot.commands.CommandController;
import studio.rrprojects.decryptbot.config.ConfigController;
import studio.rrprojects.decryptbot.discord.BotListener;
import studio.rrprojects.decryptbot.gui.CharacterAddGUI;
import studio.rrprojects.decryptbot.gui.RulesGui;

import javax.security.auth.login.LoginException;
import java.io.File;

public class MainController {
    private final ConfigController configController;
    private final JDA jda;
    private final CommandController commandController;
    private final BotListener botListener;
    private static String mainDir;

    MainController() {
        System.out.println("MAIN CONTROLLER: STARTING!");

        //Set the Directory
        SetupDirectory();

        //Grab Configs
        configController = new ConfigController(this);

        //Launch JDA
        jda = StartJDA();

        //Initialize Commands
        commandController = new CommandController(this);

        //Start Listener
        botListener = new BotListener(commandController);
        jda.addEventListener(botListener);

        //RulesGui rulesGui = new RulesGui("Rules GUI v1", this);
        //CharacterAddGUI characterAddGUI = new CharacterAddGUI("Character Add v1", this);
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

    private void SetupDirectory() {
        mainDir = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Decrypt Bot" + File.separator;

        File mainDirFile = new File(mainDir);

        if (!mainDirFile.exists()) {
            System.out.println("MAIN CONTROLLER: CREATING MAIN DIRECTORY AT " + mainDir);
            if (mainDirFile.mkdir()) {
                System.out.println("MAIN CONTROLLER: MAIN DIRECTORY CREATED SUCCESSFULLY");
            } else {
                System.out.println("MAIN CONTROLLER: ERROR: UNABLE TO CREATE MAIN DIRECTORY");
            }
        }
    }

    public JDA getJda() {
        return jda;
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public static String getMainDir() {
        return mainDir;
    }

    public ConfigController getConfigController() {
        return configController;
    }
}

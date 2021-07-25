package studio.rrprojects.decryptbot.config;

import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.constants.FileConstants;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConfigController {
    private final MainController mainController;
    private final Properties prop;
    private final File fileConfig;
    private ArrayList<ConfigOption> listConfigOptions;
    private FileReader reader;

    public ConfigController(MainController mainController) {
        this.mainController = mainController;

        String configFileName = "BotInfo.cfg";
        String configFilePath = FileConstants.HOME_DIR + configFileName;

        CreateListConfigOptions();

        fileConfig = new File(configFilePath);
        prop = new Properties();

        CheckAndCreateFile();

        LoadConfigFile();
    }

    private void LoadConfigFile() {
        System.out.println("CONFIG CONTROLLER: LOADING CONFIG FILE");

        try {
            reader = new FileReader(fileConfig);
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ConfigOption option: listConfigOptions) {
            option.setCurrentValue(prop.getProperty(option.key));
        }

        System.out.println("CONFIG CONTROLLER: CONFIG FILE LOADED");
    }

    private void CheckAndCreateFile() {
        if (!fileConfig.exists()) {
            System.out.println("CONFIG CONTROLLER: CREATING CONFIG FILE");
            try {
                if (fileConfig.createNewFile()) {
                    System.out.println("CONFIG CONTROLLER: CONFIG FILE CREATED SUCCESSFULLY");
                    PopulateNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void PopulateNewFile() {
        for (ConfigOption option: listConfigOptions) {
            prop.setProperty(option.key, option.defaultValue);
        }

        try {
            prop.store(new FileOutputStream(fileConfig), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CreateListConfigOptions() {
        listConfigOptions = new ArrayList<>();
        listConfigOptions.add(new ConfigOption("botToken", "CHANGE_THIS_VALUE_BEFORE_STARTING"));
    }

    public String getOption(String searchTerm) {
        for (ConfigOption option: listConfigOptions) {
            if (option.key.equalsIgnoreCase(searchTerm)) {
                return option.currentValue;
            }
        }
        return null;
    }

    private class ConfigOption {
        private final String key;
        private final String defaultValue;
        private String currentValue;

        public ConfigOption(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public void setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
        }
    }
}

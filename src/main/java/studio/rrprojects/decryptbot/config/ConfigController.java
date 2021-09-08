package studio.rrprojects.decryptbot.config;

import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.util_library.DebugUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ConfigController {
    private final Properties prop;
    private final File fileConfig;
    private ArrayList<ConfigOption> listConfigOptions;

    String configFileName = "BotInfo.cfg";
    String configFilePath = FileConstants.HOME_DIR + configFileName;

    public ConfigController() {

        CreateListConfigOptions();

        fileConfig = new File(configFilePath);
        prop = new Properties();

        CheckAndCreateFile();

        LoadConfigFile();
    }

    private void LoadConfigFile() {
        DebugUtils.ProgressNormalMsg("CONFIG CONTROLLER: LOADING CONFIG FILE");

        try {
            FileReader reader = new FileReader(fileConfig);
            prop.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ConfigOption option: listConfigOptions) {
            option.setCurrentValue(prop.getProperty(option.key));
        }

        DebugUtils.ProgressNormalMsg("CONFIG CONTROLLER: CONFIG FILE LOADED");
    }

    private void CheckAndCreateFile() {
        if (!fileConfig.exists()) {
            DebugUtils.ProgressNormalMsg("CONFIG CONTROLLER: CREATING CONFIG FILE");
            try {
                if (fileConfig.createNewFile()) {
                    DebugUtils.CautionMsg("CONFIG CONTROLLER: CONFIG FILE CREATED SUCCESSFULLY");
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
        listConfigOptions.add(new ConfigOption("isTesting", "false"));
    }

    public String getOption(String searchTerm) {
        for (ConfigOption option: listConfigOptions) {
            if (option.key.equalsIgnoreCase(searchTerm)) {
                return option.currentValue;
            }
        }
        return null;
    }

    private static class ConfigOption {
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

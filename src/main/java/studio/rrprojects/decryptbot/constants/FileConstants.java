package studio.rrprojects.decryptbot.constants;

import java.io.File;

public class FileConstants {

    public static final String HOME_DIR = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Decrypt Bot" + File.separator;
    public static final String JSON_DIR = HOME_DIR + "JSON" + File.separator;
    public static final String TTS_API = HOME_DIR + "TTS-API.json";
}

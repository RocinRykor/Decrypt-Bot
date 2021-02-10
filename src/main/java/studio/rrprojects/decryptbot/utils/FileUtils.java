package studio.rrprojects.decryptbot.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static File LoadFile(String path) {
        File file = new File(path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}

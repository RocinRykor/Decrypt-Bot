package studio.rrprojects.decryptbot.commands.container;

import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.util_library.DebugUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RuleObject {
    private final File file;
    private final String title;
    private final String fileType;
    private String contents;


    public RuleObject(String fileName) {
        file = new File(FileConstants.RULES_DIR + fileName);
        title = RemoveExtension(fileName);
        fileType = GetFileType(fileName);
        contents = "";

        try {
            contents = Files.readString(file.toPath());
        } catch (IOException e) {
            DebugUtils.ErrorMsg("Error Reading File: " + file);
            e.printStackTrace();
        }

        DebugUtils.UnknownMsg("Rule Loaded: " + title);
    }

    private String GetFileType(String fileName) {
        if (fileName.lastIndexOf(".") == -1) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private String RemoveExtension(String fileName) {

        if (fileName.contains(".")) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }

    }

    public File getFile() {
        return file;
    }

    public String getTitle() {
        return title;
    }

    public String getFileType() {
        return fileType;
    }

    public String getContents() {
        return contents;
    }
}

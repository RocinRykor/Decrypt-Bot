package studio.rrprojects.decryptbot.gui;

import org.json.JSONObject;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.util_library.FileUtil;
import studio.rrprojects.util_library.JSONUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class RulesGui extends JFrame {
    private final MainController mainController;

    //JFrame
    private JPanel panelMain;
    private JTextField txtFieldTitle;
    private JLabel labelTitle;
    private JTextArea txtAreaDescription;
    private JButton buttonSubmit;
    private JTextField txtFieldSource;
    private JLabel labelKeywords;
    private JTextField txtFieldKeywords;
    private JLabel labelSource;

    //JSON
    private File jsonFile;
    private FileWriter writer;
    private JSONObject mainObj;

    public RulesGui(String title, MainController mainController) {
        super(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.setSize(800, 600);

        this.mainController = mainController;

        Initialize();
    }

    private void SubmitNewRule() {
        System.out.println("RULES GUI: SUBMITTING NEW RULE");

        String title = txtFieldTitle.getText();
        String source = txtFieldSource.getText();
        String description = CleanupDescription(txtAreaDescription.getText());
        ArrayList<String> tags = ProcessKeywords(txtFieldKeywords.getText());

        JSONObject ruleContents = new JSONObject();

        ruleContents.put("source", source);
        ruleContents.put("description", description);
        ruleContents.put("keywords", tags);

        mainObj.put(title, ruleContents);

        JSONUtil.WriteJsonToFile(mainObj, jsonFile);

        System.out.println("RULES GUI: RULE SUBMITTED!");
    }

    private String CleanupDescription(String input) {
        String doubleLineBreakPlaceHolder = "=!="; //Something absurd that won't show in any valid description
        String naturalLineBreakPlaceHolder = "=+=";
        String processedString = input;

        //First replace double line breaks with my placeholder
        processedString = processedString.replaceAll("\n\n", doubleLineBreakPlaceHolder);

        //Remove natural line breaks (line break at the end of a sentence)
        processedString = processedString.replaceAll(".\n", naturalLineBreakPlaceHolder);

        //Remove all line breaks with a space
        processedString = processedString.replaceAll("\n", " ");

        //then removed the mid-word hyphen from line breaks in the middle of large words
        processedString = processedString.replaceAll("- ", "");

        //cleanup by replacing my placeholder with the double line break
        processedString = processedString.replaceAll(doubleLineBreakPlaceHolder, "\n\n");

        //and replace natural line breaks
        processedString = processedString.replaceAll(naturalLineBreakPlaceHolder, ".\n");

        return processedString;
    }

    private ArrayList<String> ProcessKeywords(String text) {
        String keywordString = text.toLowerCase();
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(keywordString.split(",")));

        ArrayList<String> outputArray = new ArrayList<>();

        for (String string : tmp) {

            String processing = string.replaceAll(",", "");
            processing = TrimSpace(processing);
            processing = processing.replaceAll(" ", "_");
            outputArray.add(processing);
        }

        return outputArray;
    }

    private String TrimSpace(String input) {
        String tmp = input;
        boolean trimSpace = true;
        while (trimSpace) {
            if (tmp.startsWith(" ")) {
                tmp = tmp.replaceFirst(" ", "");
            } else {
                trimSpace = false;
            }
        }

        return tmp;
    }

    private void Initialize() {
        LoadJSONFile();

        buttonSubmit.addActionListener(actionEvent -> {
            SubmitNewRule();
        });
        txtFieldSource.setText("SR3e P.");
    }

    private void LoadJSONFile() {
        String jsonDirString = FileConstants.JSON_DIR;

        File jsonDir = new File(jsonDirString);
        if (!jsonDir.exists()) {
            jsonDir.mkdir();
        }

        String jsonFilePath = jsonDirString + "rules_repo.json";
        jsonFile = FileUtil.loadFileFromPath(jsonFilePath);

        mainObj = JSONUtil.loadJsonFromFile(jsonFile);
    }

}

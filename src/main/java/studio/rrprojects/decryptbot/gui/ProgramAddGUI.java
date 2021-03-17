package studio.rrprojects.decryptbot.gui;

import org.json.JSONObject;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.util_library.FileUtil;
import studio.rrprojects.util_library.JSONUtil;

import javax.swing.*;
import java.io.File;

public class ProgramAddGUI extends JFrame{
    private final MainController mainController;
    private JPanel panelMain;
    private JTextField textName;
    private JSpinner spinnerMultiplier;
    private JTextArea textDescription;
    private JTextField textType;
    private JTextField textTarget;
    private JButton buttonSubmit;
    private File jsonFile;
    private JSONObject mainObj;

    public ProgramAddGUI(String title, MainController mainController) {
        super(title);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panelMain);
        this.setSize(800, 600);

        this.mainController = mainController;

        Initialize();
    }

    private void Initialize() {
        LoadJSONFile();

        buttonSubmit.addActionListener(actionEvent -> {
            SubmitNewCharacter();
        });
    }

    private void SubmitNewCharacter() {
        System.out.println("PROGRAM GUI: SUBMITTING NEW PROGRAM");

        String name = textName.getText();
        String type = textType.getText();
        String target = textTarget.getText();
        int multiplier = Integer.parseInt(spinnerMultiplier.getValue().toString());
        String description = CleanupDescription(textDescription.getText());

        JSONObject programContents = new JSONObject();

        programContents.put("type", type);
        programContents.put("target", target);
        programContents.put("multiplier", multiplier);
        programContents.put("description", description);

        mainObj.put(name, programContents);

        JSONUtil.WriteJsonToFile(mainObj, jsonFile);

        System.out.println("RULES GUI: RULE SUBMITTED!");
    }

    private String CleanupDescription(String input) {
        String doubleLineBreakPlaceHolder = "=!="; //Something absurd that wont show in any valid description
        String processedString = input;

        //First replace double line breaks with my placeholder
        processedString = processedString.replaceAll("\n\n", doubleLineBreakPlaceHolder);

        //Remove all line breaks with a space
        processedString = processedString.replaceAll("\n", " ");

        //then removed the mid word hyphen from line breaks in the middle of large words
        processedString = processedString.replaceAll("- ", "");

        //cleanup by replacing my placeholder with the double line break
        processedString = processedString.replaceAll(doubleLineBreakPlaceHolder, "\n\n");

        return processedString;
    }

    private void LoadJSONFile() {
        String jsonDirString = mainController.getMainDir() + "JSON" + File.separator;

        File jsonDir = new File(jsonDirString);
        if (!jsonDir.exists()) {
            jsonDir.mkdir();
        }

        String jsonFilePath = jsonDirString + "SR3E_decking_utilities.json";
        jsonFile = FileUtil.loadFileFromPath(jsonFilePath);

        mainObj = JSONUtil.loadJsonFromFile(jsonFile);
    }
}

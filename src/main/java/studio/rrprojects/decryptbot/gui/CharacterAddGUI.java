package studio.rrprojects.decryptbot.gui;

import org.json.JSONObject;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.utils.FileUtils;
import studio.rrprojects.decryptbot.utils.JSONUtils;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class CharacterAddGUI extends JFrame {
    private final MainController mainController;
    private JPanel panelMain;
    private JLabel labelName;
    private JTextField textFieldName;
    private JTextArea textAreaDescription;
    private JTextField textFieldCreator;
    private JLabel labelDescription;
    private JLabel labelCreator;
    private JButton buttonSubmit;
    private File jsonFile;
    private JSONObject mainObj;

    public CharacterAddGUI(String title, MainController mainController) {
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
        System.out.println("RULES GUI: SUBMITTING NEW RULE");

        String name = textFieldName.getText();
        String creator = textFieldCreator.getText();
        String description = textAreaDescription.getText();

        JSONObject ruleObj = new JSONObject();
        JSONObject ruleContents = new JSONObject();

        ruleContents.put("creator", creator);
        ruleContents.put("description", description);

        mainObj.put(name, ruleContents);

        JSONUtils.WriteJsonToFile(jsonFile, mainObj);

        System.out.println("RULES GUI: RULE SUBMITTED!");
    }

    private void LoadJSONFile() {
        String jsonDirString = mainController.getMainDir() + "JSON" + File.separator;

        File jsonDir = new File(jsonDirString);
        if (!jsonDir.exists()) {
            jsonDir.mkdir();
        }

        String jsonFilePath = jsonDirString + "character_repo.json";
        jsonFile = FileUtils.LoadFile(jsonFilePath);

        mainObj = JSONUtils.LoadJsonFromFile(jsonFile);
    }
}

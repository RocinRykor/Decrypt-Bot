package studio.rrprojects.decryptbot.gui;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.PrettyPrint;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import studio.rrprojects.decryptbot.MainController;
import studio.rrprojects.decryptbot.utils.FileUtils;
import studio.rrprojects.decryptbot.utils.JSONUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
        String description = txtAreaDescription.getText();
        ArrayList<String> tags = ProcessKeywords(txtFieldKeywords.getText());

        JSONObject ruleObj = new JSONObject();
        JSONObject ruleContents = new JSONObject();

        ruleContents.put("source", source);
        ruleContents.put("description", description);
        ruleContents.put("keywords", tags);

        ruleObj.put(title, ruleContents);

        String jsonString = ruleObj.toString();
        JsonValue writeJson = Json.parse(jsonString);

        try {
            writeJson.writeTo(writer, PrettyPrint.PRETTY_PRINT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("RULES GUI: RULE SUBMITTED!");
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
        String jsonDirString = mainController.getMainDir() + "JSON" + File.separator;

        File jsonDir = new File(jsonDirString);
        if (!jsonDir.exists()) {
            jsonDir.mkdir();
        }

        String jsonFilePath = jsonDirString + "rules_repo.json";
        jsonFile = FileUtils.LoadFile(jsonFilePath);

    }
}

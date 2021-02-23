package studio.rrprojects.decryptbot.utils;

import java.util.ArrayList;

public class MyMessageBuilder {
    ArrayList<String> message = new ArrayList<>();

    public void add(String input) {
        message.add(input);
    }

    public void addBlankLine() {
        message.add("");
    }

    public void addUnderLine() {
        message.add("[DL]");
    }

    public void addWithUnderLine(String input) {
        add(input);
        addUnderLine();
    }

    public void addAt(int index, String input) {
        message.add(index, input);
    }

    public void addBlankLineAt(int index) {
        message.add(index, "");
    }

    public void addUnderLineAt(int index) {
        message.add(index, "[DL]");
    }

    public void addWithUnderLineAt(int index, String input) {
        addAt(index, input);
        addUnderLineAt(index+1);
    }

    //Builder - Final Step
    public String build(boolean usesMarkdown) {
        int longestLineLength = 0;
        for (String line : message) {
            int lineLength = line.length();
            if (lineLength > longestLineLength) {
                longestLineLength = lineLength;
            }
        }
        StringBuilder dashLine = new StringBuilder();
        int maxLength = 25;
        if (longestLineLength > maxLength) { longestLineLength = maxLength; }
        dashLine.append("=".repeat(longestLineLength));

        StringBuilder output = new StringBuilder();

        for (String line : message) {
            String tmpLine;

            if (line.equalsIgnoreCase("[DL]")) {
                tmpLine = dashLine + "\n";
            } else {
                tmpLine = line + "\n";
            }

            output.append(tmpLine);
        }

        if (usesMarkdown) {
            output = new StringBuilder(MessageUtils.BlockText(output.toString(), "md"));
        }

        return output.toString();
    }
}
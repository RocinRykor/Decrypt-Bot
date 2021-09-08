package studio.rrprojects.decryptbot.commands.container;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.util_library.DebugUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandContainer {
    private String notes = "";
    //Input Variables
    String rawInput;
    MessageReceivedEvent event;

    String primaryCommand;

    String argumentPrefix = "--";
    ArrayList<CommandArguments> listArgs = new ArrayList<>();

    private ArrayList<String> listParameters = new ArrayList<>();

    //Output Variables
    String outputMessage = "";

    public CommandContainer(String input, MessageReceivedEvent event) {
        rawInput = input;
        this.event = event;

        if (input.contains(argumentPrefix)) {
            ProcessPrimaryString(input.substring(0, input.indexOf(argumentPrefix)));
            ProcessArguments(input.substring(input.indexOf(argumentPrefix)));
        } else {
            ProcessPrimaryString(input);
        }

        if (rawInput.contains("\"")) { //Check if beheaded string has a note and if so extract it.
            int quotationIndex = rawInput.indexOf("\"");
            String rawNotes = rawInput.substring(quotationIndex);
            notes = rawNotes.replace("\"", "");
        }
    }

    private void ProcessPrimaryString(String primaryString) {
        listParameters.addAll(Arrays.asList(primaryString.split(" ")));
        primaryCommand = listParameters.get(0);
        listParameters.remove(0);
    }

    private void ProcessArguments(String argumentString) {
        //TODO Finish off argument parsing
    }

    public void clearMessage() {
        outputMessage = "";
    }

    public void addMessageLine(String newLine) {
        outputMessage += newLine + "\n";
    }

    public String getRawInput() {
        return rawInput;
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public String getPrimaryCommand() {
        return primaryCommand;
    }

    public String getArgumentPrefix() {
        return argumentPrefix;
    }

    public ArrayList<CommandArguments> getListArgs() {
        return listArgs;
    }

    public ArrayList<String> getListParameters() {
        return listParameters;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public String getNotes() {
        return notes;
    }

    public String getPrimaryParameter() {
        if (listParameters.size() >= 1) {
            return listParameters.get(0);
        }

        return "NONE";
    }
}

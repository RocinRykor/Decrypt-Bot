package studio.rrprojects.decryptbot.commands;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandContainer {
    String primaryCommand;
    ArrayList<CommandArguments> listArgs;
    String argumentPrefix = "--";
    private ArrayList<String> listParameters;

    public CommandContainer(String input) {
        listArgs = new ArrayList<>();

        if (input.contains(argumentPrefix)) {
            ProcessPrimaryString(input.substring(0, input.indexOf(argumentPrefix)));
            ProcessArguments(input.substring(input.indexOf(argumentPrefix)));
        } else {
            ProcessPrimaryString(input);
        }
    }

    private void ProcessPrimaryString(String primaryString) {
        listParameters = new ArrayList<>();
        listParameters.addAll(Arrays.asList(primaryString.split(" ")));
        primaryCommand = listParameters.get(0);
        listParameters.remove(0);
    }

    private void ProcessArguments(String argumentString) {
        //TODO Finish off argument parsing
    }

    public String getPrimaryCommand() {
        return primaryCommand;
    }

    public ArrayList<CommandArguments> getListArgs() {
        return listArgs;
    }

    public String getArgumentPrefix() {
        return argumentPrefix;
    }

    public ArrayList<String> getListParameters() {
        return listParameters;
    }

    private class CommandArguments {
    }
}

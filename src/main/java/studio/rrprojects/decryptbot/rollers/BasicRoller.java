package studio.rrprojects.decryptbot.rollers;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class BasicRoller {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");


    public BasicRoller(CommandContainer cmd) {
        RollContainer rollContainer = CreateBlankRollContainer();

        String mainArg = cmd.getListParameters().get(0);

        defaultRoll(rollContainer);

        /*
         * Step 1: Check mainArg for a number, else check for stat or skill and import from character sheet as needed
         * Step 1a: Roll mainDicePool
         * Step 2: Check secondaryArgs for Advantage or Disadvantage and calculate as needed
         * Step 3: Check secondaryArgs for additional roll modifiers, execute as needed
         * Step 4: Check secondaryArgs for flat modifiers, calculate total as needed
         * Step 5: Build final results
         *
         * */

        if (!mainArg.equalsIgnoreCase("")) {
            //Steam 1: Check mainArg for a number, else check for stat or skill and import from character sheet as needed

            if (StartsWithDigit(mainArg)) {
                rollContainer = mainDicePoolBreakdown(mainArg, rollContainer);
            } else if (StartsWithLetter(mainArg)) {
                /*
                 * Import from character sheet
                 * */
                CharacterSheetImport(cmd, rollContainer);
            }
        }

        SecondaryPhase(rollContainer, cmd);

    }

    public void SecondaryPhase(RollContainer rollContainer, CommandContainer cmd) {
        SecondaryPhase(rollContainer, cmd, false);
    }

    public void SecondaryPhase(RollContainer rollContainer, CommandContainer cmd, Boolean isReturned) {
        //Takes the processed main argument and begins resolving any secondary arguments before finally building the completed roll.
        //This is its own function so that the attack and damage functions can use the same process

        String finalMessage;
        ArrayList<String> secondaryArgs = cmd.getListParameters();
        secondaryArgs.remove(0);

        //Step 1a: Roll mainDicePool
        rollContainer.mainRollResults = StartRoller(rollContainer.mainDicePool, rollContainer.mainDiceSides);

        //Step 2: Check secondaryArgs for Advantage or Disadvantage and calculate as needed
        AdvantageHandler(rollContainer, secondaryArgs);

        //Step 3: Check secondaryArgs for additional roll modifiers and flat modifiers
        rollContainer = ModChecker(rollContainer, secondaryArgs);

        //Step 4: Resolve any roll modifiers
        ResolveRollModifiers(rollContainer);

        if (isReturned) return;

        //Step 5: Build Results
        finalMessage = BuildResults(rollContainer, cmd);


        SendMessage(finalMessage, cmd.getEvent().getChannel(), cmd.getEvent().getAuthor());
    }

    private void CharacterSheetImport(CommandContainer cmd, RollContainer rollContainer) {
    }

    private void ResolveRollModifiers(RollContainer rollContainer) {
        if (rollContainer.modDicePool != null) {
            for (int i = 0; i < rollContainer.modDicePool.size(); i++) {
                int dicePool = rollContainer.modDicePool.get(i);
                int dieSides = rollContainer.modDiceSide.get(i);
                rollContainer.modRollResults.addAll(StartRoller(dicePool, dieSides));
            }
        }

    }

    private RollContainer ModChecker(RollContainer rollContainer, ArrayList<String> secondaryArgs) {

        //TODO FIX THIS

        /*
        if (secondaryArgs.size() > 0) {
            if (ArgCountChecker.argChecker(secondaryArgs.length, 1)) {
                for (int i = 0; i < secondaryArgs.length; i++) {
                    if (CheckModifiers(secondaryArgs[i])) {
                        rollContainer = ProcessModifier(secondaryArgs, i, rollContainer);
                    }

                    if (secondaryArgs[i].toLowerCase().contains("note")) {
                        rollContainer.showNotes = true;
                    }
                }
            }
        }
         */

        return rollContainer;
    }

    private RollContainer AdvantageHandler(RollContainer rollContainer, ArrayList<String> secondaryArg) {
        int advantageValue = CheckForAdvantage(secondaryArg, rollContainer);

        if (advantageValue != 0) {
            RollForAdvantage(rollContainer, advantageValue);
        }

        return rollContainer;
    }

    private void RollForAdvantage(RollContainer rollContainer, int advantageValue) {
        ArrayList<Integer> initialRoll = rollContainer.mainRollResults;
        ArrayList<Integer> newRoll = StartRoller(rollContainer.mainDicePool, rollContainer.mainDiceSides);

        rollContainer.notes.add("(Advantage Handler) Initial Set: " + initialRoll.toString() + " | Reroll Set: " + newRoll.toString());

        ArrayList<Integer> finalList = new ArrayList<>();

        for (int i = 0; i < initialRoll.size(); i++) {
            int oldValue = initialRoll.get(i);
            int newValue = newRoll.get(i);
            int result = Compare(oldValue, newValue, advantageValue);
            finalList.add(result);
        }



        rollContainer.mainRollResults = finalList;

    }

    private int Compare(int oldValue, int newValue, int advantageValue) {
        if (advantageValue < 0) {
            return Math.min(oldValue, newValue);
        } else {
            return Math.max(oldValue, newValue);
        }
    }

    private int CheckForAdvantage(ArrayList<String> inputList, RollContainer rollContainer) {
        int tmpValue = 0;

        if (inputList != null) {
            for (String s : inputList) {
                if (s.contains("dis")) {
                    tmpValue -= 1;
                } else if (s.contains("adv")) {
                    tmpValue += 1;
                }
            }
        }

        rollContainer.advValue += tmpValue;

        return tmpValue;
    }

    private boolean StartsWithLetter(String input) {
        return Character.isAlphabetic(input.charAt(0));
    }

    private boolean StartsWithDigit(String input) {
        return Character.isDigit(input.charAt(0));
    }

    public RollContainer ProcessModifier(String[] secondaryArgs, int index, RollContainer rollContainer) {
        String temp = secondaryArgs[index];

        if (secondaryArgs[index].length() == 1) {
            if (index + 1 < secondaryArgs.length) {
                temp += secondaryArgs[index+1];
            }
        }


        if(temp.contains("d")) {
            //Split the modifier back up
            String tmpSuffix = temp.substring(1);

            String[] splitArray;

            String tmp = tmpSuffix.replace("d", " ");
            System.out.println(tmp);
            splitArray = tmp.split(" ");

            System.out.println(splitArray[0]);

            int modDicePool = Integer.parseInt(splitArray[0]);
            int modDiceSides = Integer.parseInt(splitArray[1]);

            rollContainer.modDicePool.add(modDicePool);
            rollContainer.modDiceSide.add(modDiceSides);

            return rollContainer;
        }

        String calcString = "0" + temp;

        int result = 0;

        try {
            result = (int) engine.eval(calcString);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        rollContainer.modValue += result;

        return rollContainer;
    }

    private boolean CheckModifiers(String input) {
        return input.startsWith("+") || input.startsWith("-");
    }

    private void defaultRoll(RollContainer rollContainer) {
        rollContainer.mainDicePool = 1;
        rollContainer.mainDiceSides = 20;

        rollContainer.title = "Rolling 1, 20-Sided dice";

    }

    public RollContainer mainDicePoolBreakdown(String input, RollContainer rollContainer) {
        String[] strArray;

        if (input.contains("d")) {
            String temp = input.replace("d", " ");
            strArray = temp.split(" ");
            rollContainer.mainDicePool = Integer.parseInt(strArray[0]);
            rollContainer.mainDiceSides = Integer.parseInt(strArray[1]);
        } else {
            rollContainer.mainDicePool = 1;
            rollContainer.mainDiceSides = Integer.parseInt(input);
        }

        rollContainer.title = "Rolling " + rollContainer.mainDicePool + ", " + rollContainer.mainDiceSides + "-Sided dice";

        return rollContainer;
    }

    public ArrayList<Integer> StartRoller(int dicePool, int dieSides) {

        ArrayList<Integer> rollResults = new ArrayList<>();

        for (int i = 0; i < dicePool; i++) {
            rollResults.add(RollDice(dieSides));
        }

        return rollResults;
    }

    public String BuildResults(RollContainer rollContainer, CommandContainer cmd) {
        int mainDiceResult = 0;
        int modDiceResult = 0;
        int modValue = rollContainer.modValue;

        for (int i = 0; i < rollContainer.mainRollResults.size(); i++) {
            mainDiceResult += rollContainer.mainRollResults.get(i);
        }

        if (rollContainer.modRollResults != null) {
            for (int i = 0; i < rollContainer.modRollResults.size(); i++) {
                modDiceResult += rollContainer.modRollResults.get(i);
            }
        }

        String advMessage = "";
        if (rollContainer.advValue < 0) {
            advMessage = " with disadvantage";
        } else if (rollContainer.advValue > 0) {
            advMessage = " with advantage";
        }

        int finalResult = mainDiceResult + modDiceResult + modValue;
        StringBuilder message = new StringBuilder("Roll by: " + cmd.getEvent().getAuthor().getName() + " \n"
                + rollContainer.title + advMessage + "\n"
                + "===========\n\n"
                + "> BREAKDOWN"
                + "> Main Rolls: " + rollContainer.mainRollResults.toString() + "\n"
                + "> Total Main Roll Value: " + mainDiceResult + "\n\n"
                + "> Mod Rolls: " + rollContainer.modRollResults.toString() + "\n"
                + "> Total Mod Roll Value: " + modDiceResult + "\n\n"
                + "> Flat Mod Value: " + modValue + "\n");

        if (rollContainer.showNotes) {
            message.append("> == NOTES ==\n");
            for (String note : rollContainer.notes) {
                message.append("> ").append(note).append("\n");
            }
        }

        message.append("\nFinal Results : ").append(finalResult).append(" \n").append("===========");

        message = new StringBuilder(MessageUtils.BlockText(message.toString(), "md"));

        return message.toString(); //FIX
    }

    private void SendMessage(String message, MessageChannel DESTINATION, User user) {
        MessageUtils.SendMessage(message, DESTINATION);
    }

    private int RollDice(int sides) {

        /*//!!! CHEATING BE HAPPENING HERE
        if (Controller.getFudge()) {
            //If dice rolled is less than d20, always return max value, else roll top half of die
            if (sides < 20) {
                dieValue = sides;
            } else {
                int halfMaxValue = sides/2;
                int rollValue = (int) ((Math.random() * halfMaxValue) + 1);
                dieValue = rollValue + halfMaxValue;
            }
        }
        *///!!! END OF CHEATING
        return (int) ((Math.random() * sides) + 1);
    }

    public RollContainer CreateBlankRollContainer() {
        /*
         * This will be used to store all the information about the rolls as the bot progresses along each step of the rolling function
         * */
        return new RollContainer("", 1, 20, null, null, null, null, 0, 0, false, new ArrayList<>());
    }

    public static class RollContainer {
        public String title;

        public int mainDicePool;
        public int mainDiceSides;
        public ArrayList<Integer> mainRollResults;

        public ArrayList<Integer> modDicePool;
        public ArrayList<Integer> modDiceSide;
        public ArrayList<Integer> modRollResults;

        public int modValue;

        public int advValue;

        public Boolean showNotes;
        public ArrayList<String> notes;

        public RollContainer(String title, int mainDicePool, int mainDiceSides, ArrayList<Integer> mainRollResults,
                             ArrayList<Integer> modDicePool, ArrayList<Integer> modDiceSide, ArrayList<Integer> modRollResults, int modValue, int advValue, Boolean showNotes, ArrayList<String> notes) {
            this.title = title;

            this.mainDicePool = mainDicePool;
            this.mainDiceSides = mainDiceSides;
            this.mainRollResults = new ArrayList<>();

            this.modDicePool = new ArrayList<>();
            this.modDiceSide = new ArrayList<>();
            this.modRollResults = new ArrayList<>();

            this.modValue = modValue;
            this.advValue = advValue;

            this.showNotes = showNotes;
            this.notes = notes;
        }
    }
}

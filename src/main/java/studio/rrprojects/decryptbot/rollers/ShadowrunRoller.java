package studio.rrprojects.decryptbot.rollers;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class ShadowrunRoller {
    private final HashMap<String, Runnable> commandTable;
    RollFormatter rollFormatter;
    private ArrayList<String> listParameters;
    private final int defaultTargetNumber = 4;
    private int dicePool;
    private int targetNumber;
    private String testType;
    private MessageReceivedEvent event;
    private RollContainer rollContainer;

    public ShadowrunRoller() {
        commandTable = new HashMap<>();
        commandTable.put("Success", this::SuccessTest);
        commandTable.put("Open", this::OpenTest);
        commandTable.put("Initiative", this::InitiativeTest);

        rollFormatter = new RollFormatter();
    }

    public void Roll(CommandContainer cmd) {
        this.listParameters = cmd.getListParameters();
        this.event = cmd.getEvent();
        rollContainer = new RollContainer();


        //Check First Parameter - Needs Digit
        String mainParameterString = listParameters.get(0);
        try {
            dicePool = Integer.parseInt(mainParameterString);
        } catch (Exception e) {
            System.out.println("SHADOWRUN ROLLER: INVALID MAIN PARAMETER");
            e.printStackTrace();
            return;
        }

        //Check Secondary
        ProcessSecondaryParameter();

        commandTable.get(testType).run();
    }


    private void SuccessTest() {
        System.out.println("SHADOWRUN ROLLER: SUCCESS TEST");

        rollContainer.SuccessTest(dicePool, targetNumber);

        OutputSuccessResults(rollContainer);
    }

    private void OutputSuccessResults(RollContainer rollContainer) {
        String author = event.getAuthor().getName();
        // getAuthorPref

        rollContainer.setAuthor(author);

        MessageEmbed message = rollFormatter.Parse(rollContainer);

        MessageUtils.SendEmbedMessage(message, event.getChannel());
    }

    private void OpenTest() {
        System.out.println("SHADOWRUN ROLLER: OPEN TEST");

        rollContainer.OpenTest(dicePool);

        OutputOpenResults(rollContainer);
    }

    private void OutputOpenResults(RollContainer rollContainer) {
        String message = "Roll By: " + event.getAuthor().getName() + "\n" +
                "Dice Pool: " + rollContainer.getDicePool() + ", Target: " + "Open Test" + "\n" +
                "Rolls: " + rollContainer.getListRolls().toString() + "\n" +
                "Target Number: " + rollContainer.getTargetNumber();

        MessageUtils.SendMessage(message, event.getChannel());
    }

    private void InitiativeTest() {
        System.out.println("SHADOWRUN ROLLER: INITIATIVE TEST");

        try {
            int modifier = Integer.parseInt(listParameters.get(2));
            rollContainer.InitiativeTest(dicePool, modifier);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        OutputInitiativeResults();

    }

    private void OutputInitiativeResults() {
        String message = "Roll By: " + event.getAuthor().getName() + "\n" +
                "Dice Pool: " + rollContainer.getDicePool() + "\n" +
                "Rolls: " + rollContainer.getListRolls().toString() + "\n" +
                "Roll Results: " + rollContainer.getBase() + " + Modifier: " + rollContainer.getModifier() + "\n" +
                "Total Initiative: " + rollContainer.getTotal();

        MessageUtils.SendMessage(message, event.getChannel());
    }

    private void ProcessSecondaryParameter() {
        //First check if there is a secondary parameter
        if (listParameters.size() < 2) {
            targetNumber = defaultTargetNumber;
            SetTestType("Success");
        } else if (listParameters.get(1).equalsIgnoreCase("i")){
            SetTestType("Initiative");
        }
        else if (listParameters.get(1).equalsIgnoreCase("o")){
            SetTestType("Open");
        } else {
            try {
                targetNumber = Integer.parseInt(listParameters.get(1));
            } catch (Exception e) {
                System.out.println("SHADOWRUN ROLLER: INVALID SECONDARY PARAMETER");
                e.printStackTrace();
                return;
            }

            SetTestType("Success");
        }
    }

    private void SetTestType(String testType) {
        this.testType = testType;
    }


}

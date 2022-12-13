package studio.rrprojects.decryptbot.rollers;

import studio.rrprojects.decryptbot.utils.RollUtils;
import studio.rrprojects.util_library.DebugUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LifeLeft {
    ShadowrunRoller shadowrunRoller = new ShadowrunRoller();
    int baseLinks = 9; //The amount of people connected to the ritual still alive
    int baseTarget = 6; //This is where the target starts for each test, it will increase over time
    int rateIncrease = 4; //Every 4 tests the base increases by 1;

    LinkedHashMap<Integer, Integer> tableResults = new LinkedHashMap<>();

    // == DEBUG == //
    boolean debugMode = false;


    public void stressTest(int iterations) {
        for (int i = 0; i < iterations; i++) {
            testLife();
        }


        LinkedHashMap<Integer, Integer> sortedMap = new LinkedHashMap<>();
        tableResults.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        DebugUtils.ProgressNormalMsg(sortedMap.toString());
    }

    public void testLife() {
        int currentLinks = baseLinks;
        int currentTarget = baseTarget;

        int totalTime = 1;

        while (currentLinks > 0) {
            if (totalTime % rateIncrease == 0) {
                currentTarget += 1;
            }

            int numberOfHits = RollUtils.simpleSuccessTest(currentLinks, currentTarget);

            if (numberOfHits == 0) {
                currentLinks -= 1;
                if (debugMode) {
                    DebugUtils.CautionMsg("0 Hits -> Week: " + totalTime + " -> Target Number: " + currentTarget + " -> Current Links: " + currentLinks);
                }
            }

            totalTime += 1;

        }

        totalTime -= 1; //Removes last step, corrects an off by 1 error -> there are better ways to deal with this, I am lazy.
        AddResult(totalTime);
        if(debugMode) {
            DebugUtils.VariableMsg("Doe lived for " + totalTime + " weeks. RIP Doe");
        }
    }

    private void AddResult(int totalTime) {
        if (!tableResults.containsKey(totalTime)) {
            tableResults.put(totalTime, 0);
        }

        int oldValue = tableResults.get(totalTime);

        tableResults.replace(totalTime, oldValue, (oldValue+1));
    }
}

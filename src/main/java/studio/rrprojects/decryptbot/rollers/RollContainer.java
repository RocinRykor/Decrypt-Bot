package studio.rrprojects.decryptbot.rollers;

import studio.rrprojects.decryptbot.utils.RollUtils;

import java.util.ArrayList;
import java.util.Collections;

public class RollContainer {
    private ArrayList<Integer> listRolls;
    private int dicePool;

    //Success Test
    private int targetNumber;
    private int countHits;
    private int countsMisses;

    //Initiative Test
    private int modifier;
    private int total;
    private int base;

    public void SuccessTest(int dicePool, int targetNumber) {
        this.dicePool = dicePool;
        this.targetNumber = targetNumber;

        listRolls = new ArrayList<>();

        for (int i = 0; i < dicePool; i++) {
            int result = RollUtils.ExplodingSixRoll();
            listRolls.add(result);
            if (result >= targetNumber) {
                countHits++;
            } else if (result == 1) {
                countsMisses++;
            }
        }

        CleanUp();
    }

    private void CleanUp() {
        Collections.sort(listRolls);
    }

    public ArrayList<Integer> getListRolls() {
        return listRolls;
    }

    public int getDicePool() {
        return dicePool;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public int getCountHits() {
        return countHits;
    }

    public int getCountsMisses() {
        return countsMisses;
    }

    public void OpenTest(int dicePool) {
        SuccessTest(dicePool, 0);
        targetNumber = listRolls.get(listRolls.size() - 1);
    }

    public void InitiativeTest(int dicePool, int modifier) {
        this.dicePool = dicePool;
        this.modifier = modifier;
        listRolls = new ArrayList<>();

        base = 0;
        for (int i = 0; i < dicePool; i++) {
            int result = RollUtils.getRandomRange(1, 6);
            listRolls.add(result);
            base += result;
        }

        total = base + modifier;

        CleanUp();
    }

    public int getModifier() {
        return modifier;
    }

    public int getTotal() {
        return total;
    }

    public int getBase() {
        return base;
    }
}

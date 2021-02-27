package studio.rrprojects.decryptbot.rollers;

import studio.rrprojects.decryptbot.utils.RollUtils;
import studio.rrprojects.util_library.MathUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RollContainer {
    private String author;
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

    //Extra
    private String note = "";
    private int rollType = -1; //-1 = No Roll Set, 1 = Success, 2 = Open Test, 3 = Initiative
    private int successLevel = 0; // -1 = Critical Glitch, 0 = No Success Level, 1 = Failure, 2 = Success

    public void SuccessTest(int dicePool, int targetNumber) {
        this.dicePool = dicePool;
        this.targetNumber = targetNumber;
        rollType = 1;

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
        rollType = 2;
        targetNumber = listRolls.get(listRolls.size() - 1);
    }

    public void InitiativeTest(int dicePool, int modifier) {
        this.dicePool = dicePool;
        this.modifier = modifier;
        rollType = 3;
        listRolls = new ArrayList<>();

        base = 0;
        for (int i = 0; i < dicePool; i++) {
            int result = MathUtil.getRandomRange(1, 6);
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

    public void setAuthor(String name) {
        author = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRollType() {
        return rollType;
    }

    public void setRollType(int rollType) {
        this.rollType = rollType;
    }

    public void setSuccessLevel(int successLevel) {
        this.successLevel = successLevel;
    }

    public int getSuccessLevel() {
        return successLevel;
    }

    public String getRollTypeString() {
        //-1 = No Roll Set, 1 = Success, 2 = Open Test, 3 = Initiative
        HashMap<Integer, String> rollTypeTable = new HashMap<>();
        rollTypeTable.put(-1, "No Roll Set");
        rollTypeTable.put(1, "Success Test");
        rollTypeTable.put(2, "Open Test");
        rollTypeTable.put(3, "Initiative Roll");

        return rollTypeTable.get(rollType);
    }

    public Color getSuccessColor() {
        if (successLevel == -1) {
            //Critical Glitch
            return Color.RED;
        } else if (successLevel == 1) {
            //Failure
            return Color.ORANGE;
        } else if (successLevel == 2) {
            //Success
            return GreenScale();
        }

        System.out.println("ROLL CONTAINER: SOMETHING WENT WRONG: SUCCESS LEVEL - " + successLevel);
        return Color.PINK;
        }

    private Color GreenScale() {
        if (countHits >= 6) {
            return Color.GREEN;
        } else {
            double greenScale = (((double) countHits / 6) * 255);
            return new Color(0, (int) greenScale, 0);
        }
    }
}

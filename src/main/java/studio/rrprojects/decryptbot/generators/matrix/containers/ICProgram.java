package studio.rrprojects.decryptbot.generators.matrix.containers;

import studio.rrprojects.decryptbot.constants.Matrix;
import studio.rrprojects.decryptbot.utils.RollUtils;

import java.util.ArrayList;

public class ICProgram {
    private final String icCategory;
    private String icLevel;
    private String icType;
    private int icRating;
    private ArrayList<String> icOptions = new ArrayList<>();

    public ICProgram(String icCategory, int systemSecurityValue) {
        icRating = RollICRating(systemSecurityValue);
        this.icCategory = icCategory;

        if (icCategory != null) {
            if (icCategory.equalsIgnoreCase(Matrix.REACTIVE)) {
                icOptions.add(RollReactiveOption());
            } else {
                icOptions.add(RollProactiveOption());
            }
        }

    }

    private int RollICRating(int systemSecurityValue) {
        if (systemSecurityValue <= 4) {
            return ICRatingTable(4, 5, 6, 7);
        } else if (systemSecurityValue >= 5 && systemSecurityValue <= 7) {
            return ICRatingTable(5, 7, 9, 10);
        } else if (systemSecurityValue >= 8 && systemSecurityValue <= 10) {
            return ICRatingTable(6, 8,10, 12);
        } else {
            return ICRatingTable(8, 10, 11, 12);
        }
    }

    private int ICRatingTable(int i, int i1, int i2, int i3) {
        int roll = RollUtils.StandardRoll(2,6);

        switch (roll) {
            case 2: case 3: case 4: case 5:
                return i;
            case 6: case 7: case 8:
                return i1;
            case 9: case 10: case 11:
                return i2;
            case 12:
                return i3;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    String RollCripplerRipper() {
        int roll = RollUtils.StandardRoll(1, 6);

        switch (roll) {
            case 1: case 2:
                return Matrix.BOD;
            case 3:
                return Matrix.EVASION;
            case 4: case 5:
                return Matrix.MASKING;
            case 6:
                return Matrix.SENSOR;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    String RollReactiveOption() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 2: case 3: case 4:
                return Matrix.SHIELD;
            case 5: case 9:
                return Matrix.ARMOR;
            case 6: case 7:
                return Matrix.NONE;
            case 8:
                return Matrix.TRAP;
            case 10:case 11: case 12:
                return Matrix.SHIFT;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    String RollProactiveOption() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 2: case 3:
                return Matrix.PARTY_CLUSTER;
            case 4:
                int mod = RollUtils.StandardRoll(1,3);
                return Matrix.EXPERT_OFFENCE + "-" + mod;
            case 5:
                return Matrix.SHIFTING;
            case 6:
                return Matrix.CASCADING;
            case 7:
                return Matrix.NONE;
            case 8:
                return Matrix.ARMOR;
            case 9:
                return Matrix.SHIELDING;
            case 10:
                mod = RollUtils.StandardRoll(1,3);
                return Matrix.EXPERT_DEFENCE + "-" + mod;
            case 11:
                return Matrix.TRAP;
            case 12:
                return "ROLL TWICE";
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    String RollTrapIC() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 2:
                return Matrix.SHIELD;
            case 3: case 4: case 5:
                return Matrix.ARMOR;
            case 6: case 7: case 8:
                return Matrix.TRAP;
            case 9: case 10:case 11:
                return Matrix.SPARKY;
            case 12:
                return Matrix.BLACK + " IC";
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    public String getIcType() {
        return icType;
    }

    public void setIcType(String icType) {
        this.icType = icType;
    }

    public String getIcLevel() {
        return icLevel;
    }

    public void setIcLevel(String icLevel) {
        this.icLevel = icLevel;
    }

    @Override
    public String toString() {
        return icCategory + " " + icLevel + " -> " + icOptions + " " + icType + "-" + icRating;
    }
}

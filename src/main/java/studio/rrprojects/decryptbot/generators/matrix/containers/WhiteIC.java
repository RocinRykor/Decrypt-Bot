package studio.rrprojects.decryptbot.generators.matrix.containers;

import studio.rrprojects.decryptbot.constants.Matrix;
import studio.rrprojects.decryptbot.utils.RollUtils;

public class WhiteIC extends ICProgram{
    public WhiteIC(String icCategory, int systemSecurityValue) {
        super(icCategory, systemSecurityValue);
        setIcLevel(Matrix.WHITE);

        if (icCategory.equalsIgnoreCase(Matrix.REACTIVE)) {
            setIcType(RollReactiveWhite());
        } else {
            setIcType(RollProactiveWhite());
        }
    }

    private String RollReactiveWhite() {
        int roll = RollUtils.StandardRoll(1, 6);

        switch (roll) {
            case 1: case 2:
                return Matrix.PROBE;
            case 3: case 4: case 5:
                return Matrix.TRACE;
            case 6:
                return Matrix.TAR_BABY;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }

    }

    private String RollProactiveWhite() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 1: case 2: case 3: case 4: case 5:
                return super.RollCripplerRipper() + "-" + Matrix.CRIPPLER;
            case 6: case 7: case 8:
                return Matrix.KILLER;
            case 9: case 10: case 11:
                return Matrix.SCOUT;
            case 12:
                return Matrix.CONSTRUCT;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }

    }
}

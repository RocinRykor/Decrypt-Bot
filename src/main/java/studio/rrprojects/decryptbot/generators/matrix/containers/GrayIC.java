package studio.rrprojects.decryptbot.generators.matrix.containers;

import studio.rrprojects.decryptbot.constants.Matrix;
import studio.rrprojects.decryptbot.utils.RollUtils;

public class GrayIC extends ICProgram {
    public GrayIC(String icCategory, int systemSecurityValue) {
        super(icCategory, systemSecurityValue);
        setIcLevel(Matrix.GRAY);

        if (icCategory.equalsIgnoreCase(Matrix.REACTIVE)) {
            setIcType(RollReactive());
        } else {
            setIcType(RollProactive());
        }
    }

    private String RollReactive() {
        int roll = RollUtils.StandardRoll(1, 6);

        switch (roll) {
            case 1: case 2:
                return Matrix.TAR_PIT;
            case 3:
                return Matrix.TRACE + " /w " + Matrix.TRAP;
            case 4:
                return Matrix.PROBE + " /w " + Matrix.TRAP;
            case 5:
                return Matrix.SCOUT + " /w " + Matrix.TRAP;
            case 6:
                return Matrix.CONSTRUCT;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }

    }

    private String RollProactive() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 1: case 2: case 3: case 4: case 5:
                return super.RollCripplerRipper() + "-" + Matrix.RIPPER;
            case 6: case 7: case 8:
                return Matrix.BLASTER;
            case 9: case 10: case 11:
                return Matrix.SPARKY;
            case 12:
                return Matrix.CONSTRUCT;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }

    }
}

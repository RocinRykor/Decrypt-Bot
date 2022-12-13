package studio.rrprojects.decryptbot.generators.matrix.containers;

import studio.rrprojects.decryptbot.constants.Matrix;
import studio.rrprojects.decryptbot.utils.RollUtils;

public class BlackIC extends ICProgram {
    public BlackIC(String icCategory, int systemSecurityValue) {
        super(icCategory, systemSecurityValue);
        setIcLevel(Matrix.BLACK);

        setIcType(RollType());
    }

    private String RollType() {
        int roll = RollUtils.StandardRoll(2, 6);

        switch (roll) {
            case 1: case 2: case 3: case 4:
                return RollPsychotropic() + " " + Matrix.PSYCHOTROPIC;
            case 5: case 6: case 7:
                return Matrix.LETHAL;
            case 8: case 9: case 10:
                return Matrix.NON_LETHAL;
            case 11:
                return Matrix.CEREBROPATHIC;
            case 12:
                return Matrix.CONSTRUCT;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }

    private String RollPsychotropic() {
        int roll = RollUtils.StandardRoll(1, 6);

        switch (roll) {
            case 1: case 2:
                return Matrix.CYBERPHOBIA;
            case 3:
                return Matrix.FRENZY;
            case 4:
                return Matrix.JUDAS;
            case 5: case 6:
                return Matrix.POSITIVE_CONDITIONING;
            default:
                throw new IllegalStateException("Unexpected value: " + roll);
        }
    }
}

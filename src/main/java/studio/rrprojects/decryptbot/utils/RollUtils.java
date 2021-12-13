package studio.rrprojects.decryptbot.utils;

import studio.rrprojects.util_library.MathUtil;

public class RollUtils {

    public static Integer StandardRoll(int diceNumber, int dieSides) {
        int result = 0;
        for (int i = 0; i < diceNumber; i++) {
            result += MathUtil.getRandomRange(1, dieSides);
        }

        return result;
    }

    public static Integer ExplodingSixRoll() {
        //Keep rolling if you get a six, add all the rolls together
        int result  = 0;
        boolean explodingSix = true;

         while (explodingSix) {
             int tmp = MathUtil.getRandomRange(1, 6);
             result += tmp;
             if (tmp != 6) {
                 explodingSix = false;
             }
         }

         return result;
    }
}

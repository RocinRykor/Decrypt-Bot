package studio.rrprojects.decryptbot.utils;

import studio.rrprojects.util_library.MathUtil;

public class RollUtils {

    public static Integer ExplodingSixRoll() {
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

package studio.rrprojects.decryptbot.utils;

public class RollUtils {

    public static Integer ExplodingSixRoll() {
        int result  = 0;
        boolean explodingSix = true;

         while (explodingSix) {
             int tmp = getRandomRange(1, 6);
             result += tmp;
             if (tmp != 6) {
                 explodingSix = false;
             }
         }

         return result;
    }

    public static int getRandomRange(int min, int max) {
        return (int) ((Math.random() * (1 + max-min)) + min);
    }
}

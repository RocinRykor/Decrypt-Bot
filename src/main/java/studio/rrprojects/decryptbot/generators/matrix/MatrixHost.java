package studio.rrprojects.decryptbot.generators.matrix;

import studio.rrprojects.util_library.MathUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class MatrixHost {
    private HashMap<String, RatingContainer> tableHost;
    private HashMap<String, String> diffLevels;

    /*
    Relevant Book Info:
    HOST RATING TABLE
    Intrusion Difficulty - Security Value - Subsystem Ratings
                Easy     -   1D3 + 3      -    1D3 + 7
                Average  -   1D3 + 6      -    2D3 + 9
                Hard     -   2D3 + 6      -    1D6 + 12
     */

    //Aetreus Code
    public MatrixHost() {

        tableHost = new HashMap<>();
        tableHost.put("Easy", new RatingContainer(1, 3, 1, 3, 7));
        tableHost.put("Medium", new RatingContainer(1, 6, 2, 3, 9));
        tableHost.put("Hard", new RatingContainer(2, 6, 1, 6, 12));
        tableHost.put("Unbreakable", new RatingContainer(2, 9, 2, 6, 15));

        diffLevels = new HashMap<>();
        diffLevels.put("easy", "Easy");
        diffLevels.put("low", "Easy");
        diffLevels.put("simple", "Easy");
        diffLevels.put("light", "Easy");
        diffLevels.put("soft", "Easy");
        diffLevels.put("medium", "Medium");
        diffLevels.put("med", "Medium");
        diffLevels.put("avg", "Medium");
        diffLevels.put("average", "Medium");
        diffLevels.put("moderate", "Medium");
        diffLevels.put("firm", "Medium");
        diffLevels.put("mod", "Medium");
        diffLevels.put("hard", "Hard");
        diffLevels.put("heavy", "Hard");
        diffLevels.put("strong", "Hard");
        diffLevels.put("brutal", "Hard");
        diffLevels.put("knox", "Unbreakable");
    }

    public String GenerateHost(String target) {
        RatingContainer container = tableHost.get(target);
        int securityRating = 0;
        ArrayList<Integer> systemRatings = new ArrayList<>();

        for (int i = 0; i < container.securityDicePool; i++) {
            securityRating += MathUtil.getRandomRange(1,3);
        }
        securityRating += container.securityMod;

        for (int i = 0; i < 5; i++) {
            int systemValue = 0;
            for (int j = 0; j < container.systemDicePool; j++) {
                systemValue += MathUtil.getRandomRange(1, container.systemDiceValue);
            }
            systemValue += container.systemMod;
            systemRatings.add(systemValue);
        }

        return String.format("System: %d/%d/%d/%d/%d/%d", securityRating, systemRatings.get(0), systemRatings.get(1), systemRatings.get(2), systemRatings.get(3), systemRatings.get(4));
    }

    private static class RatingContainer {
        int securityDicePool;
        int securityMod;
        int systemDicePool;
        int systemDiceValue;
        int systemMod;

        public RatingContainer(int securityDicePool, int securityMod, int systemDicePool, int systemDiceValue, int systemMod) {
            this.securityDicePool = securityDicePool;
            this.securityMod = securityMod;
            this.systemDicePool = systemDicePool;
            this.systemDiceValue = systemDiceValue;
            this.systemMod = systemMod;
        }
    }
}

package studio.rrprojects.decryptbot.generators.magical_groups;

import studio.rrprojects.decryptbot.utils.RollUtils;
import studio.rrprojects.util_library.MathUtil;

import java.util.ArrayList;
import java.util.Collections;

public class MagicalGroup {

    private String groupName;
    private int groupSize = 0;
    private String groupRestrictions;
    private String groupPurpose;
    private ArrayList<Stricture> groupStrictures = new ArrayList<>();
    private String groupResources;
    private final ArrayList<String> listPurposes = new ArrayList<>();
    private final ArrayList<Stricture> listStrictures = new ArrayList<>();

    public MagicalGroup(String groupName) {
        this.groupName = groupName;

        initPurpose();
        initStrictureList();
    }

    private void initStrictureList() {
        listStrictures.add(new Stricture("Attendance", 1, 4, 3));
        listStrictures.add(new Stricture("Belief", 1, 6, 5));
        listStrictures.add(new Stricture("Deed", 3, 4, 4));
        listStrictures.add(new Stricture("Exclusive Membership", 2,5,4));
        listStrictures.add(new Stricture("Exclusive Ritual", 3, 5, 5));
        listStrictures.add(new Stricture("Fraternity", 4, 4, 5));
        listStrictures.add(new Stricture("Geasa", 5, 3, 3));
        listStrictures.add(new Stricture("Limited Membership", 1, 5, 2));
        listStrictures.add(new Stricture("Material Link", 2, 5, 5));
        listStrictures.add(new Stricture("Oath", 2, 5, 6));
        listStrictures.add(new Stricture("Obedience", 1, 4, 5));
        listStrictures.add(new Stricture("Sacrifice", 5, 4, 4));
        listStrictures.add(new Stricture("Secrecy", 2, 3, 5));
    }

    public void generateRandomGroup() {
        //Get Purpose
        randomPurpose();

        //Get The Size
        randomSize();

        //Get Restrictions
        randomRestrictions();

        //Strictures
        randomStrictures();

        //Resources
        randomResources();
    }

    private void randomStrictures() {
        int numberOfStrictures = roll(1);

        Collections.shuffle(listStrictures);

        for (int i = 0; i < numberOfStrictures; i++) {
            Stricture stricture = listStrictures.get(i);

            if (stricture.isAdded(groupPurpose)) {
                groupStrictures.add(stricture);
            }
        }
    }

    private void randomResources() {
        int baseValue = roll(2);

        setGroupResources(ResourceTable(baseValue));
    }

    private String ResourceTable(int baseValue) {
        switch (baseValue) {
            case 2:
                return "Street, No Patron";
            case 3: case 4:
                return "Squatter, No Patron";
            case 5: case 6:
                return "Low" + PatronCheck(1);
            case 7: case 8:
                return "Middle" + PatronCheck(1);
            case 9: case 10:
                return "High" + PatronCheck(2);
            case 11: case 12:
                return "Luxury" + PatronCheck(4);
            default:
                throw new IllegalStateException("Unexpected value: " + baseValue);
        }
    }

    private String PatronCheck(int max) {
        int roll = MathUtil.getRandomRange(1, 6);

        if (roll <= max) {
            return " with a Patron";
        }

        return ", No Patron";
    }

    private void randomRestrictions() {
        int baseValue = roll(2);

        setGroupRestrictions(RestrictionsTable(baseValue));
    }

    private String RestrictionsTable(int baseValue) {
        switch (baseValue) {
            case 2:
                return "No restrictions";
            case 3: case 4:
                return "Biological limitation: A member must be a specific race or gender";
            case 5: case 6:
                return "Religious/moral limitation: A member must subscribe to a specific religion or moral code. This would include members of political activist groups.";
            case 7: case 8:
                return "Magical tradition: This group is limited to a specific magical tradition. It can be broad as in hermetic or specific as in Snake shaman only";
            case 9: case 10:
                return "Social limitation: Members must maintain a specific lifestyle, whether based on a specific culture or work for a given corp, for example.";
            case 11: case 12:
                return "Two Limitations. DM's Choice.";
            default:
                throw new IllegalStateException("Unexpected value: " + baseValue);
        }
    }

    private void randomSize() {
        int baseValue = roll(2);
        
        setGroupSize(SizeTable(baseValue));
    }

    private int SizeTable(int baseValue) {
        switch (baseValue) {
            case 2: case 3:
                return MathUtil.getRandomRange(2, 4);
            case 4: case 5:
                return MathUtil.getRandomRange(5, 7);
            case 6: case 7: case 8:
                return MathUtil.getRandomRange(8, 12);
            case 9: case 10: case 11:
                return MathUtil.getRandomRange(13, 22);
            case 12:
                return MathUtil.getRandomRange(23, 60);
            default:
                throw new IllegalStateException("Unexpected value: " + baseValue);
        }
    }

    private void randomPurpose() {
        Collections.shuffle(listPurposes);
        setGroupPurpose(listPurposes.get(0));
    }

    private void initPurpose() {
        listPurposes.add("Initiatory");
        listPurposes.add("Dedicated");
        listPurposes.add("Conspiratorial");
    }

    private int roll(int diceNumber) {
        return RollUtils.StandardRoll(diceNumber, 6);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getGroupRestrictions() {
        return groupRestrictions;
    }

    public void setGroupRestrictions(String groupRestrictions) {
        this.groupRestrictions = groupRestrictions;
    }

    public String getGroupPurpose() {
        return groupPurpose;
    }

    public void setGroupPurpose(String groupPurpose) {
        this.groupPurpose = groupPurpose;
    }

    public ArrayList<Stricture> getGroupStrictures() {
        return groupStrictures;
    }

    public void setGroupStrictures(ArrayList<Stricture> groupStrictures) {
        this.groupStrictures = groupStrictures;
    }

    public String getGroupResources() {
        return groupResources;
    }

    public void setGroupResources(String groupResources) {
        this.groupResources = groupResources;
    }

    @Override
    public String toString() {
        return "MagicalGroup{" +
                "groupName='" + groupName + '\'' +
                ", groupSize=" + groupSize +
                ", groupRestrictions='" + groupRestrictions + '\'' +
                ", groupPurpose='" + groupPurpose + '\'' +
                ", groupStrictures=" + groupStrictures +
                ", groupResources='" + groupResources + '\'' +
                '}';
    }

    public String build() {
        String message = "";

        message += "=== " + getGroupName() + " ===\n";
        message += getGroupPurpose() + " group with " + getGroupSize() + " members.\n\n";
        message += "Resources: " + getGroupResources() + "\n\n";
        message += "Known Restrictions: " + getGroupRestrictions() + "\n\n";
        message += "Known Strictures: " + getGroupStrictures().toString() + "\n\n";

        return message;
    }

    private static class Stricture {
        private final String strictureName;
        private final int initiatoryScore;
        private final int dedicatedScore;
        private final int conspiratorialScore;

        private Stricture(String strictureName, int initiatoryScore, int dedicatedScore, int conspiratorialScore) {
            this.strictureName = strictureName;
            this.initiatoryScore = initiatoryScore;
            this.dedicatedScore = dedicatedScore;
            this.conspiratorialScore = conspiratorialScore;
        }

        public boolean isAdded(String groupPurpose) {
            switch (groupPurpose) {
                case "Initiatory":
                    return StrictureCheck(initiatoryScore);
                case "Dedicated":
                    return StrictureCheck(dedicatedScore);
                case "Conspiratorial":
                    return StrictureCheck(conspiratorialScore);
                default:
                    throw new IllegalStateException("Unexpected value: " + groupPurpose);
            }
        }

        private boolean StrictureCheck(int max) {
            int roll = MathUtil.getRandomRange(1, 6);
            return roll <= max;
        }

        @Override
        public String toString() {
            return strictureName;
        }
    }
}

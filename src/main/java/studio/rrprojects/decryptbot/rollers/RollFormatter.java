package studio.rrprojects.decryptbot.rollers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import studio.rrprojects.decryptbot.utils.MyMessageBuilder;

public class RollFormatter {


    private MyMessageBuilder builder;
    private RollContainer rollContainer;

    public void MINIMAL() {
        builder.addWithUnderLine("Pool: " + rollContainer.getDicePool() + ", TN: " + rollContainer.getTargetNumber() + ", Hits: < " + rollContainer.getCountHits() + " >");
    }

    public void STANDARD( ) {
        builder.addWithUnderLine("Dice Pool: " + rollContainer.getDicePool());
        builder.add("> Target Number: " + rollContainer.getTargetNumber());
        builder.addWithUnderLine("Hits: < " + rollContainer.getCountHits() + " >");
    }

    public void STANDARD_INVERTED() {
        builder.addWithUnderLine("Hits: < " + rollContainer.getCountHits() + " >");
        builder.add("> Target Number: " + rollContainer.getTargetNumber());
        builder.addWithUnderLine("Dice Pool: " + rollContainer.getDicePool());
    }

    public void VERBOSE() {
        builder.addWithUnderLine("Dice Pool: " + rollContainer.getDicePool());
        builder.addBlankLine();
        builder.add("> Results: " + rollContainer.getListRolls().toString());
        builder.add("> Target Number: " + rollContainer.getTargetNumber());
        builder.addBlankLine();
        builder.addWithUnderLine("Hits: < " + rollContainer.getCountHits() + " >");
    }

    public void VERBOSE_INVERTED() {
        builder.addWithUnderLine("Hits: < " + rollContainer.getCountHits() + " >");
        builder.addBlankLine();
        builder.add("> Results: " + rollContainer.getListRolls().toString());
        builder.add("> Target Number: " + rollContainer.getTargetNumber());
        builder.addBlankLine();
        builder.addWithUnderLine("Dice Pool: " + rollContainer.getDicePool());
    }

    public MessageEmbed Parse(RollContainer rollContainer) {
        builder = new MyMessageBuilder();
        this.rollContainer = rollContainer;

        /*
        This is the only spot that will change depending on formatting
        Will attempt to grab user pref, if not resorts to default style
        */
        MINIMAL();

        AddNote();
        CheckFailure();
        String bodyText = BuildBody();
        return DiscordMessageBuilder(bodyText);
    }

    private MessageEmbed DiscordMessageBuilder(String bodyText) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String author = rollContainer.getAuthor();;
        String title = rollContainer.getRollTypeString();

        embedBuilder.addField(title + " by: " + author, bodyText, false);

        if (rollContainer.getSuccessLevel() != 0) {
            embedBuilder.setColor(rollContainer.getSuccessColor());
        }

        return embedBuilder.build();
    }

    private String BuildBody() {
        return builder.build(true);
    }

    private void CheckFailure() {
        if (rollContainer.getRollType() == 1) {

            if (rollContainer.getCountsMisses() == rollContainer.getDicePool()) {
                //Critical Glitch
                rollContainer.setSuccessLevel(-1);
                builder.add("< WARNING: Critical Glitch! >");
            } else if (rollContainer.getCountHits() > 0) {
                //Success
                rollContainer.setSuccessLevel(2);
            } else {
                //Failure
                rollContainer.setSuccessLevel(1);
                builder.add("< WARNING: Failure! >");
            }
        }
    }

    private void AddNote() {
        if (rollContainer.getNote().length() > 0) {
            builder.add("> Note: " + rollContainer.getNote());
        }
    }
}
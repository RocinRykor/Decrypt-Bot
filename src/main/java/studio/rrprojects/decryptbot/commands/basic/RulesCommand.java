package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.RuleObject;
import studio.rrprojects.decryptbot.constants.FileConstants;
import studio.rrprojects.decryptbot.constants.MarkdownStyles;
import studio.rrprojects.util_library.DebugUtils;
import studio.rrprojects.util_library.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class RulesCommand extends Command {
    @Override
    public String getName() {
        return "Rules";
    }

    @Override
    public String getAlias() {
        return "rule";
    }

    @Override
    public String getHelpDescription() {
        return "Provides quick lookup to Shadowrun Rules for quick reference";
    }

    private ArrayList<RuleObject> listRules = new ArrayList<>();

    @Override
    public void Initialize() {
        super.Initialize();

        FileUtil.CreateDir(FileConstants.RULES_DIR);

        File rulesDir = new File(FileConstants.RULES_DIR);

        for (String ruleFileName : Objects.requireNonNull(rulesDir.list())) {
            RuleObject ruleObject = new RuleObject(ruleFileName);
            listRules.add(ruleObject);
        }

        //Collections.sort(listRules);

        DebugUtils.VaraibleMsg("Files in Rules Directory: " + listRules.toString());
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        if (cmd.getListParameters().isEmpty()) {
            SendBlockMessage(ListAll(), MarkdownStyles.MD, cmd.getEvent().getChannel());
            return;
        }

        String primaryArg = cmd.getListParameters().get(0);

        if (primaryArg.equalsIgnoreCase("help")) {
            executeHelp();
        } else if (primaryArg.equalsIgnoreCase("list")) {
            SendBlockMessage(ListAll(), MarkdownStyles.MD, cmd.getEvent().getChannel());
        } else if (primaryArg.equalsIgnoreCase("get")) {
            ShowRule();
        }
    }

    private void ShowRule() {
        cmd = super.getCommandContainer();

        if (cmd.getListParameters().size() < 2) {
            SendBasicMessage("Sorry Chummier, I need the rule you want to pull up.\n" +
                    "You can use the list command to pull up all the rules I have on file.", cmd.getEvent().getChannel());
            return;
        }


        String raw = cmd.getRawInput();
        String keyParameter = cmd.getListParameters().get(1);
        String searchTerms = raw.substring(raw.indexOf(keyParameter));

        DebugUtils.VaraibleMsg("Rule Search Terms: " + searchTerms);

        RuleObject selectedRule = GetRule(searchTerms);

        if (selectedRule == null) {
            SendBasicMessage("Sorry Chummer, I can't find that particular rule", cmd.getEvent().getChannel());
            return;
        }

        SendBlockMessage(selectedRule.getContents(), selectedRule.getFileType(), cmd.getEvent().getChannel());
    }

    private RuleObject GetRule(String searchTerms) {
        for (RuleObject rule : listRules) {
            if (rule.getTitle().equalsIgnoreCase(searchTerms)) {
                return rule;
            }
        }

        return null;
    }

    private String ListAll() {
        String message = "Here is a list of all the rules I have on file: \n";

        for (RuleObject rule : listRules) {
            message += "* " + rule.getTitle() + "\n";
        }

        message += "\nTo get a specific rule please use:\n" +
                "!rule get [Rule Name]\n" +
                "=====================\n";

        return message;
    }

}

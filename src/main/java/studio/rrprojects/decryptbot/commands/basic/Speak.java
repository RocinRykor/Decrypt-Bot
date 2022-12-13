package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.audio.AudioController;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.util_library.DebugUtils;

public class Speak extends Command {
    private AudioController audioController;

    @Override
    public String getName() {
        return "Speak";
    }

    @Override
    public String getAlias() {
        return "Say";
    }

    @Override
    public String getHelpDescription() {
        return "Uses Text To Speech to say things";
    }


    public void setAudioController(AudioController audioController) {
        this.audioController = audioController;
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        String notes = cmd.getNotes();

        DebugUtils.VariableMsg("Trying to speak: " + notes);

        audioController.Speak(notes);
    }
}

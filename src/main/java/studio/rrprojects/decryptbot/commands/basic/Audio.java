package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.audio.AudioController;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;
import studio.rrprojects.util_library.DebugUtils;

public class Audio extends Command {
    private AudioController audioController;

    @Override
    public String getName() {
        return "Audio";
    }

    @Override
    public String getAlias() {
        return "A";
    }

    @Override
    public String getHelpDescription() {
        return "Plays music from Youtube and other sources.";
    }

    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);

        String primaryArg = cmd.getListParameters().get(0);

        String target = cmd.getRawInput().replace(cmd.getPrimaryCommand(), "").replace(primaryArg, "").trim();

        if (primaryArg.equalsIgnoreCase("help")) {
        } else if (primaryArg.equalsIgnoreCase("join")) {
            JoinChannel();
        } else if (primaryArg.equalsIgnoreCase("leave")) {
            LeaveChannel();
        } else if (primaryArg.equalsIgnoreCase("play")) {
            LoadTrack(target);
        } else if (primaryArg.equalsIgnoreCase("skip")) {
            SkipTrack();
        } else if (primaryArg.equalsIgnoreCase("volume")) {
            ChangeVolume(target);
        }

    }

    private void ChangeVolume(String target) {
        int volume;

        volume = Integer.parseInt(target);

        if (volume >= 0 && volume <= 100) {
            audioController.setVolume(volume);
        }

    }

    private void SkipTrack() {
        DebugUtils.UnknownMsg("ATTEMPTING TO SKIP SONG");

        audioController.SkipTrack();
    }

    private void LoadTrack(String target) {
        audioController.LoadAudio(target);
    }

    private void LeaveChannel() {
        audioController.CloseConnection();
    }

    private void JoinChannel() {
        audioController.OpenConnection();
    }

    public void setAudioController(AudioController audioController) {
        this.audioController = audioController;
    }

    public AudioController getAudioController() {
        return audioController;
    }
}

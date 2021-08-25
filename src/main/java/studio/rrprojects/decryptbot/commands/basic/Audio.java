package studio.rrprojects.decryptbot.commands.basic;

import studio.rrprojects.decryptbot.audio.MusicManager;
import studio.rrprojects.decryptbot.commands.Command;

public class Audio extends Command {
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

    private final MusicManager manager = new MusicManager();
    //private final TTSHandler ttsHandler = new TTSHandler();


}

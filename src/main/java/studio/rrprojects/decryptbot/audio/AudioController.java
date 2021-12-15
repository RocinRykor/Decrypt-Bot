package studio.rrprojects.decryptbot.audio;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import studio.rrprojects.util_library.DebugUtils;

import java.io.IOException;

public class AudioController {

    private final MusicManager manager = new MusicManager();
    private MusicPlayer musicPlayer;
    private final TTSHandler ttsHandler = new TTSHandler();

    private Guild guild;

    private final int defaultVolume = 50;

    private VoiceChannel voiceChannel;

    public AudioController() {
        DebugUtils.ProgressNormalMsg("STARTING AudioController");
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
        musicPlayer = manager.getPlayer(guild);
        voiceChannel = guild.getVoiceChannels().get(0);
        manager.setGuild(guild);
    }

    public void setVolume(int volume) {
        musicPlayer.getAudioPlayer().setVolume(volume);
    }

    public void OpenConnection() {
        guild.getAudioManager().openAudioConnection(voiceChannel);
        setVolume(defaultVolume);
    }

    public void CloseConnection() {
        guild.getAudioManager().closeAudioConnection();
    }

    public void SkipTrack() {
        manager.getPlayer(guild).skipTrack();
    }

    public void LoadAudio(String path) {
        manager.loadNewTrack(path);
    }

    public void Speak(String string) {
        DebugUtils.ProgressNormalMsg("SPEAKING!");
        
        OpenConnection();
        
        try {
            LoadAudio(ttsHandler.speak(string));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

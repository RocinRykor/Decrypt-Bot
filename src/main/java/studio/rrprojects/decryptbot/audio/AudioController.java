package studio.rrprojects.decryptbot.audio;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class AudioController {
    String pathERROR = "/Audio/" + "ERROR.mp3";
    String pathSKIP = "/Audio/" + "FORCE_STOP.wav";
    String pathJOIN = "/Audio/" + "I_AM_HERE.mp3";

    private final MusicManager manager = new MusicManager();
    private MusicPlayer musicPlayer;
    private final TTSHandler ttsHandler = new TTSHandler();

    private Guild guild;

    private final int defaultVolume = 50;

    private final File fileERROR;
    private final File fileJOIN;
    private final File fileSKIP;
    private VoiceChannel voiceChannel;

    public AudioController() {
        fileERROR = loadFile(pathERROR);
        fileJOIN = loadFile(pathJOIN);
        fileSKIP = loadFile(pathSKIP);
    }

    private File loadFile(String path) {
        try {
            return new File(Objects.requireNonNull(getClass().getResource(path)).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Guild getGuild() {
        return guild;
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
    }

    public void CloseConnection() {
        guild.getAudioManager().closeAudioConnection();
    }

    public void SkipTrack() {
        LoadAudio(fileSKIP.getAbsolutePath());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        manager.getPlayer(guild).skipTrack();
    }

    public void LoadAudio(String path) {
        manager.loadNewTrack(path);
    }
}

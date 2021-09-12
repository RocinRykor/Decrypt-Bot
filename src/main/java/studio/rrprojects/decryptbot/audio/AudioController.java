package studio.rrprojects.decryptbot.audio;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import studio.rrprojects.util_library.DebugUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class AudioController {
    //String pathERROR = "/Audio/" + "ERROR.mp3";
    //String pathSKIP = "/Audio/" + "FORCE_STOP.wav";
    //String pathJOIN = "/Audio/" + "I_AM_HERE.mp3";

    private final MusicManager manager = new MusicManager();
    private MusicPlayer musicPlayer;

    private Guild guild;

    private final int defaultVolume = 50;

    private VoiceChannel voiceChannel;

    public AudioController() {
        //fileERROR = loadFile(pathERROR);
        //fileJOIN = loadFile(pathJOIN);
        //fileSKIP = loadFile(pathSKIP);
    }

    private File loadFile(String path) {
        //TODO - So this part doesnt quite work becuase it doesn't function outside of the IDE, to do that I need an InputStream

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
        //LoadAudio(fileSKIP); //Can't load a file here to get Absolulte path becuause it needs to be an InputStream

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

    public void Speak(String string) {
        DebugUtils.ProgressNormalMsg("SPEAKING!");
        
        OpenConnection();
        
        try {
            LoadAudio(TTSHandler.speak(string));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

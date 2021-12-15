package studio.rrprojects.decryptbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

public class MusicPlayer {

    private final AudioPlayer audioPlayer;
    private final AudioListener listener;

    public MusicPlayer(AudioPlayer audioPlayer){
        this.audioPlayer = audioPlayer;
        listener = new AudioListener(this);
        audioPlayer.addListener(listener);
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public AudioPlayerSendHandler getAudioHandler(){
        return new AudioPlayerSendHandler(audioPlayer);
    }

    public synchronized void playTrack(AudioTrack track){
        listener.queue(track);
    }

    public synchronized void skipTrack(){
        listener.nextTrack();
    }
}

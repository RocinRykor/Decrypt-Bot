package studio.rrprojects.decryptbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import studio.rrprojects.util_library.DebugUtils;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    private Guild guild;

    public MusicManager() {
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild) {
        if (!players.containsKey(guild.getId()))
            players.put(guild.getId(), new MusicPlayer(manager.createPlayer()));
        return players.get(guild.getId());
    }

    public void loadNewTrack(String path) {
        DebugUtils.ProgressNormalMsg("LOADING A NEW TRACK:");

        MusicPlayer player = getPlayer(guild);
        guild.getAudioManager().setSendingHandler(player.getAudioHandler());
        manager.loadItemOrdered(player, path, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                player.playTrack(audioTrack);
                DebugUtils.VaraibleMsg("TRACK LOADED: " + audioTrack.getInfo());
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                for (int i = 0; i < playlist.getTracks().size() && i < 5; i++) {
                    AudioTrack track = playlist.getTracks().get(i);
                    player.playTrack(track);
                }

                DebugUtils.VaraibleMsg("PLAYLIST LOADED: " + playlist.getName());
            }

            @Override
            public void noMatches() {
                DebugUtils.CautionMsg("NO MATCHES");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                DebugUtils.ErrorMsg("LOAD FAILED: " + e);
            }
        });
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
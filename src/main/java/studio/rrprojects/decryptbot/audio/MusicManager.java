package studio.rrprojects.decryptbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import studio.rrprojects.util_library.DebugUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    private boolean success = true;
    private String statusMessage = "";
    private Guild guild;

    public MusicManager(){
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild) {
        if(!players.containsKey(guild.getId())) players.put(guild.getId(), new MusicPlayer(manager.createPlayer(), guild));
        return players.get(guild.getId());
    }

    public void loadTrack(final TextChannel channel, final String source){
        MusicPlayer player = getPlayer(channel.getGuild());

        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioHandler());

        manager.loadItemOrdered(player, source, new AudioLoadResultHandler(){

            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
                setSuccess(true);

                setStatusMessage("Track Loaded!");
                DebugUtils.VaraibleMsg("TRACK LOADED");
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                StringBuilder builder = new StringBuilder();
                builder.append("Adding Playlist: **").append(playlist.getName()).append("**\n");

                for(int i = 0; i < playlist.getTracks().size() && i < 5; i++){
                    AudioTrack track = playlist.getTracks().get(i);
                    builder.append("\n  **->** ").append(track.getInfo().title);
                    player.playTrack(track);
                }

                setSuccess(true);
                setStatusMessage(builder.toString());
                DebugUtils.VaraibleMsg("PLAYLIST LOADED");
            }

            @Override
            public void noMatches() {
                DebugUtils.CautionMsg("NO MATCH FOUND!");
                setSuccess(false);
                channel.sendMessage("The track " + source + " was not found.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                DebugUtils.CautionMsg("TRACK LOAD FAILED!");
                setSuccess(false);
                channel.sendMessage(("Playback error: " + exception.getMessage()+")")).queue();
            }
        });
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void loadNewTrack(String path) {
        DebugUtils.ProgressNormalMsg("LOADING A NEW TRACK:");

        MusicPlayer player = getPlayer(guild);
        DebugUtils.VaraibleMsg("MusicPlayer: " + player);

        guild.getAudioManager().setSendingHandler(player.getAudioHandler());
        DebugUtils.VaraibleMsg("SendingHandler: " + guild.getAudioManager().getSendingHandler());

        manager.loadItemOrdered(player, path, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                player.playTrack(audioTrack);
                DebugUtils.VaraibleMsg("TRACK LOADED");
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                DebugUtils.UnknownMsg("PLAYLIST LOADED");
            }

            @Override
            public void noMatches() {
                DebugUtils.CautionMsg("NO MATHCES");
            }

            @Override
            public void loadFailed(FriendlyException e) {
                DebugUtils.ErrorMsg("LOAD FAILED");
            }
        });
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
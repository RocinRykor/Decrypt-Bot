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

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

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
                //BotController.SetPresence(track.getInfo().title);
                player.playTrack(track);
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

                channel.sendMessage(builder.toString()).queue();
            }

            @Override
            public void noMatches() {
                channel.sendMessage("The track " + source + " was not found.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Playback error: " + exception.getMessage()+")").queue();
            }
        });
    }

}

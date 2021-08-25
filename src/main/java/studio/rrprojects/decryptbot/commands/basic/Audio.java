package studio.rrprojects.decryptbot.commands.basic;

import net.dv8tion.jda.api.entities.*;
import studio.rrprojects.decryptbot.audio.MusicManager;
import studio.rrprojects.decryptbot.commands.Command;
import studio.rrprojects.decryptbot.commands.container.CommandContainer;

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


    @Override
    public void executeMain(CommandContainer cmd) {
        super.executeMain(cmd);
        String primaryArg = cmd.getListParameters().get(0);

        if (primaryArg.equalsIgnoreCase("help")) {
            return;
        } else if (primaryArg.equalsIgnoreCase("join")) {
            JoinChannel(cmd);
        } else if (primaryArg.equalsIgnoreCase("leave")) {
            LeaveChannel(cmd);
        } else if (primaryArg.equalsIgnoreCase("play")) {
            LoadTrack(cmd);
        } else if (primaryArg.equalsIgnoreCase("skip")) {
            SkipTrack(cmd);
        } else if (primaryArg.equalsIgnoreCase("volume")) {
            ChangeVolume(cmd);
        } else if (primaryArg.equalsIgnoreCase("speak")) {
            //SpeakMessage(cmd);
        } else {
            //SendMessage("I don't understand your command.", channel);
        }
    }

    private void ChangeVolume(CommandContainer cmd) {
    }

    private void SkipTrack(CommandContainer cmd) {
    }

    private void LoadTrack(CommandContainer cmd) {
        MessageChannel channel = cmd.getEvent().getTextChannel();
        Guild guild = channel.getJDA().getGuilds().get(0);
        User user = cmd.getEvent().getAuthor();
        String source = cmd.getNotes();
        if(guild == null) return;

        JoinChannel(cmd);

        if (source != null) {
            manager.loadTrack((TextChannel) channel, source);
        }
    }

    private void LeaveChannel(CommandContainer cmd) {
    }

    private void JoinChannel(CommandContainer cmd) {
        Guild guild = cmd.getEvent().getJDA().getGuilds().get(0);
        User user = cmd.getEvent().getAuthor();

        if(!guild.getAudioManager().isConnected() && !guild.getAudioManager().isConnected()){
            VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
            if(voiceChannel == null){
                SendBasicMessage("You must be connect to a voice channel.", cmd.getEvent().getChannel());
            }
            guild.getAudioManager().openAudioConnection(voiceChannel);
        }
    }
}

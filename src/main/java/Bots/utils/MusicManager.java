package Bots.utils;

import java.awt.Color;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MusicManager {
	
	private static final int PLAYLIST_LIMIT = 1000;
	private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
	private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();
	private static final Map<Guild, VoiceChannel> channels = new HashMap<>();
	
	public MusicManager() {
		AudioSourceManagers.registerRemoteSources(MANAGER);
	}
	
	public AudioPlayer createPlayer(Guild g) {
		AudioPlayer p = MANAGER.createPlayer();
		TrackManager m = new TrackManager(p);
		p.addListener(m);
		
		g.getAudioManager().setSendingHandler(new PlayerSendHandler(p));
		PLAYERS.put(g, new AbstractMap.SimpleEntry<>(p, m));
		
		return p;
	}
	
	private boolean hasPlayer(Guild g) {
		return PLAYERS.containsKey(g);
	}
	
	public AudioPlayer getPlayer(Guild g) {
		if (hasPlayer(g)) {
			return PLAYERS.get(g).getKey();
		} else {
			return createPlayer(g);
		}
	}
	
	public TrackManager getManager(Guild g) {
		return PLAYERS.get(g).getValue();
	}
	
	public VoiceChannel getChannel(Guild guild) {
		return channels.get(guild);
	}
	
	public boolean isIdle(Guild g) {
		return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
	}
	
	public void loadTrack(String identifier, Member author) {
		Guild guild = author.getGuild();
		getPlayer(guild);
		channels.put(guild, author.getVoiceState().getChannel());
		
		MANAGER.setFrameBufferDuration(5000);
		MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {
			public void trackLoaded(AudioTrack track) {
				getManager(guild).queue(track, author);
			}
			
			public void playlistLoaded(AudioPlaylist playlist) {
				for (int i = 0; i < (playlist.getTracks().size() > PLAYLIST_LIMIT ? PLAYLIST_LIMIT : playlist.getTracks().size()); i++)
					getManager(guild).queue(playlist.getTracks().get(i), author);
			}
			
			public void noMatches() {
				
			}
			
			public void loadFailed(FriendlyException exception) {
				exception.printStackTrace();
			}
		});
	}
	
	public void skip(Guild g) {
		getPlayer(g).stopTrack();
		channels.remove(g);
	}
	
	public String getTimestamp(long milis) {
		long seconds = milis / 1000;
		long hours = Math.floorDiv(seconds, 3600);
		seconds = seconds - (hours * 3600);
		long mins = Math.floorDiv(seconds, 60);
		seconds = seconds - (mins * 60);
		return (hours == 0 ? "" : hours + ":") + String.format("%02d",  mins) + ":" + String.format("%02d", seconds);
	}
	
	public String buildQueueMessage(AudioInfo info) {
		AudioTrackInfo trackInfo = info.getTrack().getInfo();
		String title = trackInfo.title;
		long length = trackInfo.length;
		return "`[ " + getTimestamp(length) + " ]` " + title + "\n";
	}
	
	private void sendErrorMsg(MessageReceivedEvent event, String content) {
		event.getTextChannel().sendMessage(
			new EmbedBuilder().setColor(Color.red).setDescription(content).build()).queue();
	}
}
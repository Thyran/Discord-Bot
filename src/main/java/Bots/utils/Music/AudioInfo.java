package Bots.utils.Music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Member;

public class AudioInfo {
	
	private AudioTrack track;
	private Member author;
	
	public AudioInfo(AudioTrack track, Member author) {
		this.track = track;
		this.author = author;
	}
	
	public AudioTrack getTrack() { return track; }
	public Member getAuthor() { return author; }
}
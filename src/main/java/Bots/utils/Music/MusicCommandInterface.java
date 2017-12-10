package Bots.utils.Music;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Member;

public class MusicCommandInterface {
	public static MusicManager musicManager = new MusicManager();
	public static String lastTrack = "";
	public static List<Member> skipVotedMembers = new ArrayList<Member>();
}
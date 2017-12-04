package Bots.utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;

public class InputEvent {
	private final Guild guild;
	private final MessageChannel channel;
	private final User author;
	private final Member member;
	
	public InputEvent(GuildMessageReceivedEvent event) {
		guild = event.getGuild();
		channel = (MessageChannel) event.getChannel();
		author = event.getAuthor();
		member = event.getMember();
	}
	
	public InputEvent(GuildJoinEvent event) {
		guild = event.getGuild();
		channel = null;
		author = null;
		member = null;
	}
	
	public InputEvent(UserOnlineStatusUpdateEvent event) {
		guild = event.getGuild();
		channel = null;
		author = null;
		member = null;
	}
	
	public Guild getGuild() { return guild; }
	public MessageChannel getChannel() { return channel; }
	public TextChannel getTextChannel() { return (TextChannel) channel; }
	public PrivateChannel getPrivateChannel() { return (PrivateChannel) channel; }
	public User getAuthor() { return author; }
	public Member getMember() { return member; } 
}
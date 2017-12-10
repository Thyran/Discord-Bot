package Bots.utils;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;

public class InputEvent {
	private Guild guild;
	private Channel channel;
	private User author;
	private Member member;
	private Message message;
	
	public InputEvent() {
		guild = null;
		channel = null;
		author = null;
		member = null;
		message = null;
	}
	
	public InputEvent(GuildMessageReceivedEvent event) {
		guild = event.getGuild();
		channel = (Channel) event.getChannel();
		author = event.getAuthor();
		member = event.getMember();
		message = event.getMessage();
	}
	
	public InputEvent(GuildJoinEvent event) {
		guild = event.getGuild();
		channel = null;
		author = null;
		member = null;
		message = null;
	}
	
	public InputEvent(UserOnlineStatusUpdateEvent event) {
		guild = event.getGuild();
		channel = null;
		author = null;
		member = null;
		message = null;
	}
	
	public InputEvent setGuild(Guild guild) {
		this.guild = guild;
		return this;
	}
	
	public InputEvent setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}
	
	public InputEvent setAuthor(User author) {
		this.author = author;
		return this;
	}
	
	public InputEvent setMember(Member member) {
		this.member = member;
		return this;
	}
	
	public InputEvent setMessage(Message message) {
		this.message = message;
		return this;
	}
	
	public Guild getGuild() { return guild; }
	public MessageChannel getChannel() { return (MessageChannel)channel; }
	public TextChannel getTextChannel() { return (TextChannel) channel; }
	public PrivateChannel getPrivateChannel() { return (PrivateChannel) channel; }
	public VoiceChannel getVoiceChannel() { return (VoiceChannel)channel; }
	public User getAuthor() { return author; }
	public Member getMember() { return member; }
	public Message getMessage() { return message; }
}
package Bots.utils;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

public class Voids {
	public static void sendMessageToPrivateChannel(String message, PrivateMessageReceivedEvent event) {
		if (!event.getAuthor().isBot())
			event.getChannel().sendMessage(message).queue();
	}
	
	public static void sendMessageToCurrentChannel(String message, GuildMessageReceivedEvent event) {
		if (!event.getAuthor().isBot())
			event.getChannel().sendMessage(message).queue();
	}
}
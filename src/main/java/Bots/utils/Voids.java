package Bots.utils;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

public class Voids {
	public static void sendMessageToCurrentChannel(String message, InputEvent event) {
		if (!event.getAuthor().isBot())
			event.getChannel().sendMessage(message).queue();
	}
}
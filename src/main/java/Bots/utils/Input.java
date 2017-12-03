package Bots.utils;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Input {
	private String[] lastInput = null;
	private GuildMessageReceivedEvent event = null;
	
	public Input setLastInput(String[] commandParams) {
		lastInput = commandParams;
		return this;
	}
	
	public Input setLastEvent(GuildMessageReceivedEvent event) {
		this.event = event;
		return this;
	}
	
	public String[] getLastInput() {
		return lastInput;
	}
	
	public GuildMessageReceivedEvent getLastEvent() {
		return event;
	}
}
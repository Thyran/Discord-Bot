package Bots.utils;

import Bots.testingBot.Settings;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandExecuter {
	private ExecutionSettings settings = new ExecutionSettings();
	
	public static Execution exec = new Execution() {
		public void onExecution() {	}
	};
	
	public void setSettings(ExecutionSettings settings) {
		this.settings = settings;
	}
	
	public void executeCommand(GuildMessageReceivedEvent event) {
		if (ExecutionSettings.checkChannel) {
			if (Settings.isChannelSet() && event.getChannel().getName().equals(Settings.getChannel())) {
				exec.onExecution();
			} else {
				if (Settings.isChannelSet()) {
					Voids.sendMessageToCurrentChannel("Use the Bot´s channel for commands: " + Settings.getChannel(), event);
				} else {
					Voids.sendMessageToCurrentChannel("Set a channel for the Bot first, command: setChannel", event);
				}
			}
		} else {
			exec.onExecution();
		}
	}
}
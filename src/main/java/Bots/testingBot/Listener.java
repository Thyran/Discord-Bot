package Bots.testingBot;

import java.util.Random;

import Bots.utils.Command;
import Bots.utils.Commands;
import Bots.utils.CommandExecuter;
import Bots.utils.Input;
import Bots.utils.InputEvent;
import Bots.utils.Execution;
import Bots.utils.ExecutionSettings;
import Bots.utils.Settings;
import Bots.utils.Voids;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	
	CommandExecuter executer = new CommandExecuter();
	
	@Override
	public void onReady(ReadyEvent event) {
		executer.loadSettings();
	}
	
	@Override
	public void onShutdown(ShutdownEvent event) {
		executer.saveSettings();
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		final Command command = Commands.runCommand;
		final Input lastInput = new Input().setLastInput(null).setLastEvent(new InputEvent(event));
		
		executer.executeCommand(lastInput, command);
	}
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}
	
	@Override
	public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
		String commandStr = event.getMessage().getContent();
		
		if (commandStr.charAt(0) == '-') {
			commandStr = commandStr.substring(1);
			final String[] commandParams = commandStr.split(" ");
			
			Input lastInput = new Input().setLastInput(commandParams).setLastEvent(new InputEvent(event));

			try {
				final Command command = (Command) Commands.map.get(commandParams[0]);
				
				executer.executeCommand(lastInput, command);
			} catch (NullPointerException e) {
				Voids.sendMessageToCurrentChannel("command not known: " + commandParams[0], new InputEvent(event));
			}
		}
	}
}
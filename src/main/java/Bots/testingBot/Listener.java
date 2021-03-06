package Bots.testingBot;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandArchive;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandExecution;
import Bots.utils.Commands.Commands;
import Bots.utils.Input.Input;
import Bots.utils.Input.InputEvent;
import Bots.utils.Settings.SettingsIO;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.StatusChangeEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	
	CommandExecuter executer = new CommandExecuter();
	
	@Override
	public void onStatusChange(StatusChangeEvent event) {
		if (event.getStatus() == Status.CONNECTED) {
			SettingsIO.loadSettings(executer);
			final Command command = CommandArchive.runCommand;
			
			for (Guild guild : event.getJDA().getGuilds()) {
				final Input lastInput = new Input().setLastInput(null).setLastEvent(new InputEvent().setGuild(guild));
				
				executer.executeCommand(new CommandExecution(lastInput, command));
			}
		} else if (event.getStatus() == Status.DISCONNECTED) {
			SettingsIO.saveSettings(executer);
		}
	}
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}
	
	@Override
	public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
		String commandStr = event.getMessage().getContent();
		
		try {
			if (commandStr.charAt(0) == Commands.CommandChar) {
				commandStr = commandStr.substring(1);
				final String[] commandParams = commandStr.split(" ");
				
				Input lastInput = new Input().setLastInput(commandParams).setLastEvent(new InputEvent(event));
		
				try {
					final Command command = (Command) Commands.map.get(commandParams[0]);
					
					executer.executeCommand(new CommandExecution(lastInput, command));
				} catch (NullPointerException e) {
					Voids.sendMessageToCurrentChannel("command not known: " + commandParams[0], new InputEvent(event));
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			
		}
	}
}
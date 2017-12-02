package Bots.testingBot;

import java.util.Random;

import Bots.utils.Command;
import Bots.utils.Commands;
import Bots.utils.CommandExecuter;
import Bots.utils.Execution;
import Bots.utils.ExecutionSettings;
import Bots.utils.Voids;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}
	
	@Override
	public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
		String command = event.getMessage().getContent();
		
		if (command.charAt(0) == '-') {
			command = command.substring(1);
			final String[] commandParams = command.split(" ");
		
			String message = "";
			CommandExecuter executer = new CommandExecuter();
			
			try {
				switch (((Command) Commands.map.get(commandParams[0])).getCommand()) {
				case 0:	// Reroll command
					executer.setSettings(new ExecutionSettings().setCheckChannel(true));
					executer.exec = new Execution() {
						public void onExecution() {
							Voids.sendMessageToCurrentChannel("executing command: Roll", event);
							
							try {
								String[] names = commandParams[1].split(",");
							
								Random rn = new Random();
								int number = rn.nextInt(names.length);
							
								Voids.sendMessageToCurrentChannel(names[number] + " was chosen by the power of coincidence", event);
							
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("Roll command needs parameter (@Param: List of Players)", event);
							}
						}
					};
					break;
				case 1: // Play command
					executer.setSettings(new ExecutionSettings().setCheckChannel(true));
					executer.exec = new Execution() {
						public void onExecution() {
							Voids.sendMessageToCurrentChannel("executing command: Play", event);
						}
					};
					break;
				case 2: // setChannel command
					executer.setSettings(new ExecutionSettings().setCheckChannel(false));
					executer.exec = new Execution() {
						public void onExecution() {
							if (event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Admin", true).get(0)).contains(event.getMember())) {
								try {
									Settings.setChannel(commandParams[1]);
								} catch (ArrayIndexOutOfBoundsException e) {
									Voids.sendMessageToCurrentChannel("SetChannel command needs Parameter (@Param: Name)", event);
								}
							} else {
								Voids.sendMessageToCurrentChannel("You don´t have permission to execute command: setChannel", event);
							}
						}
					};
					break;
				case 3: // setPermissionRole command
					break;
				case 4: // grantPermission command
					break;
				default:
					Voids.sendMessageToCurrentChannel("command not known: " + command, event);
					break;
				}
				executer.executeCommand(event);
			} catch (NullPointerException e) {
				Voids.sendMessageToCurrentChannel("command not known: " + command, event);
			}
		}
	}
}
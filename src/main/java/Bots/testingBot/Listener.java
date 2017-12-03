package Bots.testingBot;

import java.util.Random;

import Bots.utils.Command;
import Bots.utils.Commands;
import Bots.utils.CommandExecuter;
import Bots.utils.Execution;
import Bots.utils.ExecutionSettings;
import Bots.utils.Voids;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	
	CommandExecuter executer = new CommandExecuter();
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}
	
	@Override
	public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
		String commandStr = event.getMessage().getContent();
		
		if (commandStr.charAt(0) == '-') {
			commandStr = commandStr.substring(1);
			final String[] commandParams = commandStr.split(" ");
		
			String message = "";
			try {
				final Command command = (Command) Commands.map.get(commandParams[0]);
				executer.setSettings(new ExecutionSettings().setCheckChannel(command.requiresCheckChannel())
						.setPermission(command.requiresPermission()));
				
				switch (command.getCommand()) {
				case 0:	// Reroll command
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
					executer.exec = new Execution() {
						public void onExecution() {
							Voids.sendMessageToCurrentChannel("executing command: Play", event);
						}
					};
					break;
				case 2: // setChannel command
					executer.exec = new Execution() {
						public void onExecution() {
							try {
								Settings.setChannel(commandParams[1]);
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("SetChannel command needs Parameter (@Param: Name)", event);
							}
						}
					};
					break;
				case 3: // addRolePermission command
					executer.exec = new Execution() {
						public void onExecution() {
							try {
								executer.addPermittedRole(commandParams[1]);
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("setPermissionRole command needs Parameter (@Param: RoleName)", event);
							}
						}
					};
					break;
				case 4: // addUserPermission command
					executer.exec = new Execution() {
						public void onExecution() {
							try {
								executer.addPermittedUser(commandParams[1]);
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("addUserPermission command needs Parameter (@Param: UserName)", event);
							}
						}
					};
					break;
				case 5: // run command
					executer.exec = new Execution() {
						public void onExecution() {
							executer.addPermittedUser(event.getGuild().getOwner().getUser().getName());
						}
					};
					break;
				case 6: // removeRolePermission command
					executer.exec = new Execution() {
						public void onExecution() {
							try {
								executer.removePermittedRole(commandParams[1], event);
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("setPermissionRole command needs Parameter (@Param: RoleName)", event);
							}
						}
					};
					break;
				case 7: // removeUserPermission command
					executer.exec = new Execution() {
						public void onExecution() {
							try {
								executer.removePermittedUser(commandParams[1], event);
							} catch (ArrayIndexOutOfBoundsException e) {
								Voids.sendMessageToCurrentChannel("removeUserPermission command needs Parameter (@Param: UserName)", event);
							}
						}
					};
					break;
				default:
					executer.setSettings(new ExecutionSettings().setCheckChannel(true).setPermission(false));
					executer.exec = new Execution() {
						public void onExecution() {
							Voids.sendMessageToCurrentChannel("command not known: " + command.getName(), event);
						}
					};
					break;
				}
				executer.executeCommand(event, command);
			} catch (NullPointerException e) {
				Voids.sendMessageToCurrentChannel("command not known: " + commandParams[0], event);
			}
		}
	}
}
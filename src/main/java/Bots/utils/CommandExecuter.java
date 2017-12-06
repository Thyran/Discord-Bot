package Bots.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.GenericGuildEvent;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandExecuter {
	
	private static final String settingsFile = "Settings.txt";
	private static final String userPermissionStr = "UserPermission = ";
	private static final String rolePermissionStr = "RolePermission = ";
	
	private ExecutionSettings settings = new ExecutionSettings();
	
	public ArrayList<String> permittedRoles = new ArrayList<String>();
	public ArrayList<String> permittedUsers = new ArrayList<String>();
	
	public CommandExecuter setSettings(ExecutionSettings settings) {
		this.settings = settings;
		return this;
	}
	
	public void addPermittedRole(String roleName, InputEvent event) {
		permittedRoles.add(event.getGuild().getName() + "." + roleName);
	}
	
	public void addPermittedRole(String roleName, String guildName) {
		permittedRoles.add(guildName + "." + roleName);
	}
	
	public void removePermittedRole(String roleName, InputEvent event) {
		try {
			permittedRoles.remove(permittedRoles.indexOf(event.getGuild().getName() + "." + roleName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("Role " + roleName + " does not have permission", event);
		}
	}
	
	public void addPermittedUser(String userName, InputEvent event) {
		permittedUsers.add(event.getGuild().getName() + "." + userName);
	}
	
	public void addPermittedUser(String userName, String guildName) {
		permittedUsers.add(guildName + "." + userName);
	}
	
	public void removePermittedUser(String userName, InputEvent event) {
		try {
			permittedUsers.remove(permittedUsers.indexOf(event.getGuild().getName() + "." + userName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("User " + userName + " does not have permission", event);
		}
	}
	
	private boolean checkMemberPermission(InputEvent event) {
		boolean result = false;
		for (String n : permittedRoles) {
			for (Member m : (event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName(n.substring(event.getGuild().getName().length()), false)))) {
				if (m.equals(event.getMember())) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			for (String u : permittedUsers) {
				for (Member m : (event.getGuild().getMembersByName(u.substring(event.getGuild().getName().length()), false))) {
					if (m.equals(event.getMember())) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	private void checkForPermission(Input lastInput, Command command) {
		if (ExecutionSettings.permission) {
			if (checkMemberPermission(lastInput.getLastEvent())) {
				checkForChannel(lastInput, command);
			} else {
				Voids.sendMessageToCurrentChannel("You don´t have permission to execute command: " + command.getName(), lastInput.getLastEvent());
			}
		} else {
			checkForChannel(lastInput, command);
		}
	}
	
	private void checkForChannel(Input lastInput, Command command) {
		if (ExecutionSettings.checkChannel) {
			if (ChannelSettings.isChannelSet() && (lastInput.getLastEvent().getChannel().getName().equals(ChannelSettings.getChannel()))) {
				execute(lastInput, command);
			} else {
				if (ChannelSettings.isChannelSet()) {
					Voids.sendMessageToCurrentChannel("Use the Bot´s channel for commands: " + ChannelSettings.getChannel(), lastInput.getLastEvent());
				} else {
					Voids.sendMessageToCurrentChannel("Set a channel for the Bot first, command: setChannel", lastInput.getLastEvent());
				}
			}
		} else {
			execute(lastInput, command);
		}
	}
	
	private void execute(Input lastInput, Command command) {
		if (command.showExecMessage())
			Voids.sendMessageToCurrentChannel("Executing command: " + command.getName(), lastInput.getLastEvent());
		
		try {
			command.getExec().onExecution(lastInput, this);
		} catch (Exception e) {
			command.getExec().onError(lastInput);
		}
	}
	
	public void executeCommand(Input lastInput, Command command) {
		this.setSettings(new ExecutionSettings().setCheckChannel(command.requiresCheckChannel())
			.setPermission(command.requiresPermission()));
		
		checkForPermission(lastInput, command);
	}
}
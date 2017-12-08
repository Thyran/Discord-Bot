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
	
	private ExecutionSettings settings = new ExecutionSettings();
	
	public ArrayList<String> permittedRoles = new ArrayList<String>();
	public ArrayList<String> permittedUsers = new ArrayList<String>();
	public ArrayList<String> guildChannels  = new ArrayList<String>();
	
	public CommandExecuter setSettings(ExecutionSettings settings) {
		this.settings = settings;
		return this;
	}
	
	private boolean doesRoleExist(String roleName, String guildName) {
		boolean result = false;
		
		for (String role : permittedRoles) {
			String[] params = role.split("/");
			if (params[0].equals(guildName) && params[1].equals(roleName)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void addPermittedRole(String roleName, InputEvent event) {
		if (!doesRoleExist(roleName, event.getGuild().getName()))
			permittedRoles.add(event.getGuild().getName() + "/" + roleName);
	}
	
	public void addPermittedRole(String roleName, String guildName) {
		if (!doesRoleExist(roleName, guildName))
			permittedRoles.add(guildName + "/" + roleName);
	}
	
	public void removePermittedRole(String roleName, InputEvent event) {
		try {
			permittedRoles.remove(permittedRoles.indexOf(event.getGuild().getName() + "/" + roleName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("Role " + roleName + " does not have permission", event);
		}
	}
	
	private boolean doesChannelExist(String channelName, String guildName) {
		boolean result = false;
		
		for (String s : guildChannels) {
			String[] params = s.split("/");
			if (params[0].equals(guildName) && params[1].equals(channelName)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void setGuildChannel(String channelName, InputEvent event) {
		if (doesChannelExist(channelName, event.getGuild().getName())) {
			int index = guildChannels.indexOf(event.getGuild().getName() + "/" + channelName);
			guildChannels.set(index, event.getGuild().getName() + "/" + channelName);
		} else {
			guildChannels.add(event.getGuild().getName() + "/" + channelName);
		}
	}
	
	public void setGuildChannel(String channelName, String guild) {
		if (doesChannelExist(channelName, guild)) {
			int index = guildChannels.indexOf(guild + "/" + channelName);
			guildChannels.set(index, guild + "/" + channelName);
		} else {
			guildChannels.add(guild + "/" + channelName);
		}
	}
	
	private boolean doesUserExist(String userName, String guildName) {
		boolean result = false;
		
		for (String user : permittedUsers) {
			String[] params = user.split("/");
			if (params[0].equals(guildName) && params[1].equals(userName)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void addPermittedUser(String userName, InputEvent event) {
		if (!doesUserExist(userName, event.getGuild().getName()))
			permittedUsers.add(event.getGuild().getName() + "/" + userName);
	}
	
	public void addPermittedUser(String userName, String guildName) {
		if (!doesUserExist(userName, guildName))
			permittedUsers.add(guildName + "/" + userName);
	}
	
	public void removePermittedUser(String userName, InputEvent event) {
		try {
			permittedUsers.remove(permittedUsers.indexOf(event.getGuild().getName() + "/" + userName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("User " + userName + " does not have permission", event);
		}
	}
	
	private boolean checkMemberPermission(InputEvent event) {
		boolean result = false;
		for (String n : permittedRoles) {
			String[] params = n.split("/");
			for (Member m : (event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName(params[1], false)))) {
				if (m.equals(event.getMember())) {
					result = true;
					break;
				}
			}
			if (result)
				break;
		}
		if (!result) {
			for (String u : permittedUsers) {
				String[] params = u.split("/");
				for (Member m : (event.getGuild().getMembersByName(params[1], false))) {
					if (m.getUser().getName().equals(event.getMember().getUser().getName())) {
						result = true;
						break;
					}
				}
				if (result)
					break;
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
	
	private boolean checkChannelSet(InputEvent event) {
		boolean result = false;
		
		String channel = getChannelFromGuild(event);
		if (event.getChannel().getName().equals(channel)) {
			result = true;
		}
		
		return result;
	}
	
	private String getChannelFromGuild(InputEvent event) {
		String result = "";
		
		for (String channel : guildChannels) {
			String[] params = channel.split("/");
			
			if (event.getGuild().getName().equals(params[0])) {
				result = params[1];
				break;
			}
		}
		
		return result;
	}
	
	private void checkForChannel(Input lastInput, Command command) {
		if (ExecutionSettings.checkChannel) {
			if (checkChannelSet(lastInput.getLastEvent())) {
				execute(lastInput, command);
			} else {
				if (!getChannelFromGuild(lastInput.getLastEvent()).equals("")) {
					Voids.sendMessageToCurrentChannel("Use the Bot´s channel for commands: " + getChannelFromGuild(lastInput.getLastEvent()), lastInput.getLastEvent());
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
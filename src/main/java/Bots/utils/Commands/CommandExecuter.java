package Bots.utils.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import Bots.utils.Voids;
import Bots.utils.Input.InputEvent;
import Bots.utils.Settings.ExecutionSettings;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.GenericGuildEvent;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandExecuter {
	
	private ExecutionSettings settings = new ExecutionSettings();
	private Queue<CommandExecution> commandQueue = new LinkedBlockingQueue<CommandExecution>();
	
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
	
	private void checkForPermission(CommandExecution execution) {
		if (ExecutionSettings.permission) {
			if (checkMemberPermission(execution.getLastInput().getLastEvent())) {
				checkForChannel(execution);
			} else {
				Voids.sendMessageToCurrentChannel("You don´t have permission to execute command: " + execution.getCommand().getName(), execution.getLastInput().getLastEvent());
			}
		} else {
			checkForChannel(execution);
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
	
	private void checkForChannel(CommandExecution execution) {
		if (ExecutionSettings.checkChannel) {
			if (checkChannelSet(execution.getLastInput().getLastEvent())) {
				execute(execution);
			} else {
				if (!getChannelFromGuild(execution.getLastInput().getLastEvent()).equals("")) {
					Voids.sendMessageToCurrentChannel("Use the Bot´s channel for commands: " + 
						getChannelFromGuild(execution.getLastInput().getLastEvent()), execution.getLastInput().getLastEvent());
				} else {
					Voids.sendMessageToCurrentChannel("Set a channel for the Bot first, command: setChannel", execution.getLastInput().getLastEvent());
				}
			}
		} else {
			execute(execution);
		}
	}
	
	private void execute(CommandExecution execution) {
		if (execution.getCommand().showExecMessage()) {
			String paramString = "";
			try {
				if (execution.getLastInput().getLastInput().length > 1) {
					paramString += ", with params: ";
					for (int i = 1; i < execution.getLastInput().getLastInput().length -1; i++)
						paramString += execution.getLastInput().getLastInput()[i] + ", ";
					paramString += execution.getLastInput().getLastInput()[execution.getLastInput().getLastInput().length -1];
				}
			} catch (NullPointerException e) {
				paramString = "";
			}
			Voids.sendMessageToCurrentChannel("Executing command: " + execution.getCommand().getName() + 
				paramString, execution.getLastInput().getLastEvent());
		}
		
		try {
			execution.getCommand().onExecution(execution.getLastInput(), this);
		} catch (Exception e) {
			execution.getCommand().onError(execution.getLastInput());
		}
	}
	
	public void executeCommand(CommandExecution execution) {
		this.setSettings(new ExecutionSettings().setCheckChannel(execution.getCommand().requiresCheckChannel())
			.setPermission(execution.getCommand().requiresPermission()));
		
		checkForPermission(execution);
		
		while (!commandQueue.isEmpty()) {
			CommandExecution exec = commandQueue.poll();
			this.setSettings(new ExecutionSettings().setCheckChannel(exec.getCommand().requiresCheckChannel())
				.setPermission(exec.getCommand().requiresPermission()));
			
			checkForPermission(exec);
		}
	}
	
	public void appendCommandExecution(CommandExecution execution) {
		commandQueue.add(execution);
	}
}
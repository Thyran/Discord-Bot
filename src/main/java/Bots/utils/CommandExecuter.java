package Bots.utils;

import java.util.ArrayList;

import Bots.testingBot.Settings;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.GenericGuildEvent;
import net.dv8tion.jda.core.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandExecuter {
	private ExecutionSettings settings = new ExecutionSettings();
	private ArrayList<String> permittedRoles = new ArrayList<String>();
	private ArrayList<String> permittedUsers = new ArrayList<String>();
	
	public static Execution exec = new Execution() {
		public void onExecution() {	}
	};
	
	public CommandExecuter setSettings(ExecutionSettings settings) {
		this.settings = settings;
		return this;
	}
	
	public void addPermittedRole(String roleName) {
		permittedRoles.add(roleName);
	}
	
	public void removePermittedRole(String roleName, GuildMessageReceivedEvent event) {
		try {
			permittedRoles.remove(permittedRoles.indexOf(roleName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("Role " + roleName + " does not have permission", event);
		}
	}
	
	public void addPermittedUser(String userName) {
		permittedUsers.add(userName);
	}
	
	public void removePermittedUser(String userName, GuildMessageReceivedEvent event) {
		try {
			permittedUsers.remove(permittedUsers.indexOf(userName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("User " + userName + " does not have permission", event);
		}
	}
	
	private boolean checkMemberPermission(GuildMessageReceivedEvent event) {
		boolean result = false;
		for (String n : permittedRoles) {
			for (Member m : (event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName(n, false)))) {
				if (m.equals(event.getMember())) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			for (String u : permittedUsers) {
				for (Member m : (event.getGuild().getMembersByName(u, false))) {
					if (m.equals(event.getMember())){
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	private void checkForPermission(GuildMessageReceivedEvent event, Command command) {
		if (ExecutionSettings.permission) {
			if (checkMemberPermission(event)) {
				checkForChannel(event);
			} else {
				Voids.sendMessageToCurrentChannel("You don´t have permission to execute command: " + command.getName(), event);
			}
		} else {
			checkForChannel(event);
		}
	}
	
	private void checkForChannel(GuildMessageReceivedEvent event) {
		if (ExecutionSettings.checkChannel) {
			if (Settings.isChannelSet() && (event.getChannel().getName().equals(Settings.getChannel()))) {
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
	
	public void executeCommand(GuildMessageReceivedEvent event, Command command) {
		checkForPermission(event, command);
	}
}
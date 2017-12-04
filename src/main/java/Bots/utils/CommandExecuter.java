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
	private ArrayList<String> permittedRoles = new ArrayList<String>();
	private ArrayList<String> permittedUsers = new ArrayList<String>();
	
	public CommandExecuter setSettings(ExecutionSettings settings) {
		this.settings = settings;
		return this;
	}
	
	public void loadSettings() {
		try {
			FileReader fr = new FileReader("../testingBot/src/main/" + settingsFile);
			BufferedReader br = new BufferedReader(fr);
			
			String currentLine;
			while((currentLine = br.readLine()) != null && currentLine.length() != 0) {
				String temp = currentLine;
				
				if (temp.substring(0, userPermissionStr.length() -1) == userPermissionStr) {
					currentLine = currentLine.substring(userPermissionStr.length());
					try {
						String[] params = currentLine.split(", ");
						for (String s : params) {
							this.permittedUsers.add(s);
						}
					} catch (Exception e) {
						System.out.println("Fehler beim laden der Einstellungen");
					}
				} else if (temp.substring(0, rolePermissionStr.length() -1) == rolePermissionStr) {
					currentLine = currentLine.substring(rolePermissionStr.length());
					try {
						String[] params = currentLine.split(", ");
						for (String s : params) {
							this.permittedRoles.add(s);
						}
					} catch (Exception e) {
						System.out.println("Fehler beim Laden der Einstellungen");
					}
				}
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println("Datei nicht gefunden: " + settingsFile);
		}
	}
	
	public void saveSettings() {
		try {
			FileWriter fw = new FileWriter("../testingBot/src/main/" + settingsFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			String permittedUsersStr = "";
			String permittedRolesStr = "";
			
			for (String user : permittedUsers) {
				permittedUsersStr += user + ", ";
			}
			
			for (String role : permittedRoles) {
				permittedRolesStr += role + ", ";
			}
			
			bw.flush();
			bw.newLine();
			bw.write(userPermissionStr + permittedUsersStr);
			bw.newLine();
			bw.write(rolePermissionStr + permittedRolesStr);
			
			bw.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern der Einstellungen");
		}
	}
	
	public void addPermittedRole(String roleName) {
		permittedRoles.add(roleName);
	}
	
	public void removePermittedRole(String roleName, InputEvent event) {
		try {
			permittedRoles.remove(permittedRoles.indexOf(roleName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("Role " + roleName + " does not have permission", event);
		}
	}
	
	public void addPermittedUser(String userName) {
		permittedUsers.add(userName);
	}
	
	public void removePermittedUser(String userName, InputEvent event) {
		try {
			permittedUsers.remove(permittedUsers.indexOf(userName));
		} catch (ArrayIndexOutOfBoundsException e) {
			Voids.sendMessageToCurrentChannel("User " + userName + " does not have permission", event);
		}
	}
	
	private boolean checkMemberPermission(InputEvent event) {
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
			if (Settings.isChannelSet() && (lastInput.getLastEvent().getChannel().getName().equals(Settings.getChannel()))) {
				execute(lastInput, command);
			} else {
				if (Settings.isChannelSet()) {
					Voids.sendMessageToCurrentChannel("Use the Bot´s channel for commands: " + Settings.getChannel(), lastInput.getLastEvent());
				} else {
					Voids.sendMessageToCurrentChannel("Set a channel for the Bot first, command: setChannel", lastInput.getLastEvent());
				}
			}
		} else {
			execute(lastInput, command);
		}
	}
	
	private void execute(Input lastInput, Command command) {
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
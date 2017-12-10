package Bots.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Benedikt
 * @param
 *     Command command = new Command(commandName, commandID, executionMessage?, checkChannels?, checkPermission?, Execution);
 */
public class Commands {

	public static HashMap map = new HashMap();
	public static char CommandChar = '-';
	public static ArrayList<Command> commands = new ArrayList<Command>();
	public static ArrayList<String> keys = new ArrayList<String>();
	
	public static void init() {
		loadCommands();
	}
	
	public static boolean isUseInCommand(Command c, String use) {
		boolean result = false;
		
		Command command = null;
		try {
			command = (Command)map.get(use);
		} catch (NullPointerException e) {
			return false;
		}
		
		result = (command == c);
		return result;
	}
	
	public static void addUseToCommand(int commandID, String use) {
		for (Command c : commands) {
			if (c.getCommand() == commandID) {
				if (!isUseInCommand(c, use)) {
					map.put(use, c);
					keys.add(use);
				}
			}
		}
	}
	
	public static void removeUseFromCommand(int commandID, String use) {
		for (Command c : commands) {
			if (c.getCommand() == commandID) {
				if (isUseInCommand(c, use)) {
					map.remove(use, c);
					keys.remove(use);
				}
			}
		}
	}
	
	public static int getCommandID(String command) {
		int result = -1;
		
		for (Command c : commands) {
			if (c.getName().equals(command)) {
				result = c.getCommand();
				break;
			}
		}
		
		return result;
	}
	
	private static void loadCommands() {
		commands.add(CommandArchive.rerollCommand);
		commands.add(CommandArchive.setChannelCommand);
		commands.add(CommandArchive.addRolePermissionCommand);
		commands.add(CommandArchive.addUserPermissionCommand);
		commands.add(CommandArchive.runCommand);
		commands.add(CommandArchive.removeRolePermissionCommand);
		commands.add(CommandArchive.removeUserPermissionCommand);
		commands.add(CommandArchive.loadSettingsCommand);
		commands.add(CommandArchive.saveSettingsCommand);
		commands.add(CommandArchive.showPermittedUsersCommand);
		commands.add(CommandArchive.showPermittedRolesCommand);
		commands.add(CommandArchive.showChannelsCommand);
		commands.add(CommandArchive.addUseCommand);
		commands.add(CommandArchive.removeUseCommand);
		commands.add(CommandArchive.showCommandsCommand);
		commands.add(CommandArchive.showCommandUsesCommand);
		commands.add(CommandArchive.playTrackCommand);
		commands.add(CommandArchive.skipTrackCommand);
		commands.add(CommandArchive.stopTrackCommand);
		commands.add(CommandArchive.shufflePlaylistCommand);
		commands.add(CommandArchive.currentTrackCommand);
		commands.add(CommandArchive.currentQueueCommand);
		commands.add(CommandArchive.lastTrackCommand);
		commands.add(CommandArchive.testCommand);
	}
}
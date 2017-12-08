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
	
	private static final String commandsFile = "Commands.txt";
	
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
		commands.add(CommandArchive.playCommand);
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
		
		/*try {
			FileReader fr = new FileReader("../testingBot/src/main/" + commandsFile);
			BufferedReader br = new BufferedReader(fr);
			
			String currentLine;
			while((currentLine = br.readLine()) != null && currentLine.length() != 0) {
				String[] args = currentLine.split(" ");
				
				for (Command c : commands) {
					if (c.getCommand() == new Integer(args[2])) {
						map.put(args[0], c);
					}
				}
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println("Datei nicht gefunden: " + commandsFile);
		}*/
	}
}
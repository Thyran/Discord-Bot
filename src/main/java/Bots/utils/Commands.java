package Bots.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Commands {
	
	private static final String commandsFile = "Commands.txt";
	
	public static HashMap map = new HashMap();
	public static final char CommandChar = '-';
	public static ArrayList<Command> commands = new ArrayList<Command>();
	
	public static final Command rerollCommand = new Command("Roll", 0, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String[] names = lastInput.getLastInput()[1].split(",");
		
			Random rn = new Random();
			int number = rn.nextInt(names.length);
		
			Voids.sendMessageToCurrentChannel(names[number] + " was chosen by the power of coincidence", lastInput.getLastEvent());
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Roll command needs parameter (@Param: List of Players)", lastInput.getLastEvent());
		}
	});
	
	public static final Command playCommand = new Command("Play", 1, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
		}
		
		public void onError(Input lastInput) {
		}
	});
	
	public static final Command setChannelCommand = new Command("setChannel", 2, false, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			Settings.setChannel(lastInput.getLastInput()[1]);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("SetChannel command needs Parameter (@Param: Name)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addRolePermissionCommand = new Command("addRolePermission", 3, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedRole(lastInput.getLastInput()[1]);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("setPermissionRole command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addUserPermissionCommand = new Command("addUserPermission", 4, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastInput()[1]);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("addUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command runCommand = new Command("run", 5, false, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastEvent().getGuild().getOwner().getUser().getName());
		}
		
		public void onError(Input lastInput) {
			
		}
	});
	
	public static final Command removeRolePermissionCommand = new Command("removeRolePermission", 6, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("setPermissionRole command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command removeUserPermissionCommand = new Command("removeUserPermission", 7, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedUser(lastInput.getLastInput()[1], lastInput.getLastEvent());
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("removeUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static void init() {
		loadCommands();
	}
	
	private static void loadCommands() {
		
		commands.add(rerollCommand);
		commands.add(playCommand);
		commands.add(setChannelCommand);
		commands.add(addRolePermissionCommand);
		commands.add(addUserPermissionCommand);
		commands.add(runCommand);
		commands.add(removeRolePermissionCommand);
		commands.add(removeUserPermissionCommand);
		
		try {
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
		}
	}
}
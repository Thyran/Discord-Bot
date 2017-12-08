package Bots.utils;

import java.util.Random;

public class CommandArchive {
	public static final Command rerollCommand = new Command("Roll", 0, true, true, false, new Execution() {
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
	
	public static final Command playCommand = new Command("Play", 1, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
		}
		
		public void onError(Input lastInput) {
		}
	});
	
	public static final Command setChannelCommand = new Command("SetChannel", 2, true, false, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.setGuildChannel(lastInput.getLastEvent().getChannel().getName(), lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("SetChannel command needs Parameter (@Param: Name)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addRolePermissionCommand = new Command("AddRolePermission", 3, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddRolePermission command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addUserPermissionCommand = new Command("AddUserPermission", 4, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command runCommand = new Command("Run", 5, false, false, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastEvent().getGuild().getOwner().getUser().getName(), lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			System.out.println("Fehler beim Start");
		}
	});
	
	public static final Command removeRolePermissionCommand = new Command("RemoveRolePermission", 6, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveRolePermission command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command removeUserPermissionCommand = new Command("RemoveUserPermission", 7, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedUser(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command loadSettingsCommand = new Command("LoadSettings", 8, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			SettingsIO.loadSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Laden der Einstellungen", lastInput.getLastEvent());
		}
	});
	
	public static final Command saveSettingsCommand = new Command("SaveSettings", 9, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Speichern der Einstellungen", lastInput.getLastEvent());
		}
	});
	
	public static final Command showPermittedUsersCommand = new Command("ShowPermittedUsers", 10, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String user : executer.permittedUsers) {
				Voids.sendMessageToCurrentChannel(user, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der User", lastInput.getLastEvent());
		}
	});
	
	public static final Command showPermittedRolesCommand = new Command("ShowPermittedRoles", 11, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String role : executer.permittedRoles) {
				Voids.sendMessageToCurrentChannel(role, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Rollen", lastInput.getLastEvent());
		}
	});
	
	public static final Command showChannelsCommand = new Command("ShowChannels", 12, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String channel : executer.guildChannels) {
				Voids.sendMessageToCurrentChannel(channel, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Channels", lastInput.getLastEvent());
		}
	});
	
	public static final Command addUseCommand = new Command("AddUse", 13, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			String use = lastInput.getLastInput()[2];
			
			Commands.addUseToCommand(Commands.getCommandID(command), use);
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddUse command needs Paramters (@Param: commandName, use)", lastInput.getLastEvent());
		}
	});
	
	public static final Command removeUseCommand = new Command("RemoveUse", 14, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			String use = lastInput.getLastInput()[2];
			
			Commands.removeUseFromCommand(Commands.getCommandID(command), use);
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveUse command needs Paramters (@Param: commandName, use)", lastInput.getLastEvent());
		}
	});
	
	public static final Command showCommandsCommand = new Command("ShowCommands", 15, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (Command c : Commands.commands) {
				Voids.sendMessageToCurrentChannel(c.getName(), lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Commands", lastInput.getLastEvent());
		}
	});
	
	public static final Command showCommandUsesCommand = new Command("ShowUses", 16, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			
			for (Command c : Commands.commands) {
				if (c.getCommand() == Commands.getCommandID(command)) {
					for (String key : Commands.keys) {
						if (Commands.isUseInCommand(c, key)) {
							Voids.sendMessageToCurrentChannel(key, lastInput.getLastEvent());
						}
					}
				}
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("ShowUse command needs Parameters (@Param: commandName)", lastInput.getLastEvent());
		}
	});
}
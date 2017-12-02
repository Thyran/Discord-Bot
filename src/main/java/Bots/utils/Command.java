package Bots.utils;

public class Command {
	
	private int command = -1;
	private boolean permission = true;
	
	public Command(int command, boolean permission) {
		this.command = command;
		this.permission = permission;
	}
	
	public int getCommand() { return command; }
	public boolean requiresPermission() { return permission; }
}
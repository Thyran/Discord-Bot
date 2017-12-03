package Bots.utils;

public class Command {
	
	private String name = "";
	private int command = -1;
	private boolean checkChannel = true;
	private boolean permission = true;
	
	public Command(String name, int command, boolean checkChannel, boolean permission) {
		this.name = name;
		this.command = command;
		this.checkChannel = checkChannel;
		this.permission = permission;
	}
	
	public String getName() { return name; }
	public int getCommand() { return command; }
	public boolean requiresCheckChannel() { return checkChannel; }
	public boolean requiresPermission() { return permission; }
}
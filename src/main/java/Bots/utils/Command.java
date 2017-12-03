package Bots.utils;

public class Command {
	
	private String name = "";
	private int command = -1;
	private boolean checkChannel = true;
	private boolean permission = true;
	private Execution exec;
	
	public Command(String name, int command, boolean checkChannel, boolean permission, Execution exec) {
		this.name = name;
		this.command = command;
		this.checkChannel = checkChannel;
		this.permission = permission;
		this.exec = exec;
	}
	
	public String getName() { return name; }
	public int getCommand() { return command; }
	public boolean requiresCheckChannel() { return checkChannel; }
	public boolean requiresPermission() { return permission; }
	public Execution getExec() { return exec; }
}
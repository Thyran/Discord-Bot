package Bots.utils.Commands;

import Bots.utils.Input.Input;

public abstract class Command {
	
	protected String name = "";
	protected int command = -1;
	protected boolean showExecMessage = true;
	protected boolean checkChannel = true;
	protected boolean permission = true;
	
	protected Command(String name, int command, boolean showExecMessage, boolean checkChannel, boolean permission) {
		this.name = name;
		this.command = command;
		this.showExecMessage = showExecMessage;
		this.checkChannel = checkChannel;
		this.permission = permission;
	}
	
	public String getName() { return name; }
	public int getCommand() { return command; }
	public boolean showExecMessage() { return showExecMessage; }
	public boolean requiresCheckChannel() { return checkChannel; }
	public boolean requiresPermission() { return permission; }

	public abstract void onExecution(Input lastInput, CommandExecuter executer);
	public abstract void onError(Input lastInput);
}
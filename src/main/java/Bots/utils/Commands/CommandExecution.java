package Bots.utils.Commands;

import Bots.utils.Input.Input;

public class CommandExecution {
	
	private Input lastInput;
	private Command command;
	
	public CommandExecution(Input lastInput, Command command) {
		this.lastInput = lastInput;
		this.command = command;
	}

	public Input getLastInput() { return lastInput; }
	public Command getCommand() { return command; }
}
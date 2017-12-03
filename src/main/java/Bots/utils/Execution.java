package Bots.utils;

import Bots.utils.Input;

public interface Execution {
	public void onExecution(Input lastInput, CommandExecuter executer);
	public void onError(Input lastInput);
}
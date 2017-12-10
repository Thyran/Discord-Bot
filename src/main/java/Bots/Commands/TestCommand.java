package Bots.Commands;

import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;

public class TestCommand extends Command {

	public TestCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("Test", CommandIDs.TEST_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
	}

	@Override
	public void onError(Input lastInput) {
	}
}

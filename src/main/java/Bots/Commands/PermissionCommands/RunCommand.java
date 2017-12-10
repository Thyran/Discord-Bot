package Bots.Commands.PermissionCommands;

import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Settings.SettingsIO;

public class RunCommand  extends Command {

	public RunCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("Run", CommandIDs.RUN_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		executer.addPermittedUser(lastInput.getLastEvent().getGuild().getOwner().getUser().getName(), lastInput.getLastEvent());
		SettingsIO.saveSettings(executer);
	}

	@Override
	public void onError(Input lastInput) {
		System.out.println("Fehler beim Start");
	}
}

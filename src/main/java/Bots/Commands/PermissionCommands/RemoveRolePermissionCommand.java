package Bots.Commands.PermissionCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Settings.SettingsIO;
import net.dv8tion.jda.core.EmbedBuilder;

public class RemoveRolePermissionCommand extends Command {

	public RemoveRolePermissionCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("RemoveRolePermission", CommandIDs.REMOVE_ROLE_PERMISSION_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		executer.removePermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
		SettingsIO.saveSettings(executer);
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "RemoveRolePermission command verlangt Parameter (@Param: Rollenname)", false)
			.build(), lastInput.getLastEvent());
	}
}

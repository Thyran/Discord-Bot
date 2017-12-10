package Bots.Commands.PermissionCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShowPermittedRolesCommand extends Command {

	public ShowPermittedRolesCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShowPermittedRoles", CommandIDs.SHOW_PERMITTED_ROLES_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		for (String role : executer.permittedRoles) {
			Voids.sendMessageToCurrentChannel(role, lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Ausgabe Fehler", "Fehler beim Ausgeben der Rolepermissions", false)
			.build(), lastInput.getLastEvent());
	}
}

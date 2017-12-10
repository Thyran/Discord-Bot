package Bots.Commands.PermissionCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShowPermittedUsersCommand extends Command {

	public ShowPermittedUsersCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShowPermittedUsers", CommandIDs.SHOW_PERMITTED_USERS_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		for (String user : executer.permittedUsers) {
			Voids.sendMessageToCurrentChannel(user, lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Ausgabe Fehler", "Fehler beim Ausgeben der Userpermissions", false)
			.build(), lastInput.getLastEvent());
	}
}

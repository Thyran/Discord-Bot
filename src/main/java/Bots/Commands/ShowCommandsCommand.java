package Bots.Commands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Commands.Commands;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShowCommandsCommand extends Command{
	public ShowCommandsCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShowCommands", CommandIDs.SHOW_COMMANDS_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		for (Command c : Commands.commands) {
			Voids.sendMessageToCurrentChannel(c.getName(), lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Ausgabe Fehler", "Fehler beim Ausgeben der Commands", false)
			.build(), lastInput.getLastEvent());
	}
}

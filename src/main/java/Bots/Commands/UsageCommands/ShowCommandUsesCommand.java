package Bots.Commands.UsageCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Commands.Commands;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShowCommandUsesCommand extends Command {

	public ShowCommandUsesCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShowCommandUses", CommandIDs.SHOW_COMMAND_USES_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		String command = lastInput.getLastInput()[1];
		
		for (Command c : Commands.commands) {
			if (c.getCommand() == Commands.getCommandID(command)) {
				for (String key : Commands.keys) {
					if (Commands.isUseInCommand(c, key)) {
						Voids.sendMessageToCurrentChannel(key, lastInput.getLastEvent());
					}
				}
			}
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "ShowUse command verlangt Parameter (@Param: Commandname)", false)
			.build(), lastInput.getLastEvent());
	}

}

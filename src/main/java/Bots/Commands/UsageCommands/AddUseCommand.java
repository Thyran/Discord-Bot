package Bots.Commands.UsageCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Commands.Commands;
import Bots.utils.Input.Input;
import Bots.utils.Settings.SettingsIO;
import net.dv8tion.jda.core.EmbedBuilder;

public class AddUseCommand extends Command {
	
	public AddUseCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("AddUse", CommandIDs.ADD_USE_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		String command = lastInput.getLastInput()[1];
		String use = lastInput.getLastInput()[2];
		
		Commands.addUseToCommand(Commands.getCommandID(command), use);
		SettingsIO.saveSettings(executer);
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "AddUse command verlangt Parameter (@Param: Commandname, use)", false)
			.build(), lastInput.getLastEvent());
	}
}

package Bots.Commands.SettingsCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Settings.SettingsIO;
import net.dv8tion.jda.core.EmbedBuilder;

public class LoadSettingsCommand extends Command {

	public LoadSettingsCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("LoadSettings", CommandIDs.LOAD_SETTINGS_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		SettingsIO.loadSettings(executer);
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Einstellungen Fehler", "Fehler beim Laden der Einstellungen", false)
			.build(), lastInput.getLastEvent());
	}
}

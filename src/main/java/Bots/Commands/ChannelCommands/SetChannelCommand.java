package Bots.Commands.ChannelCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Settings.SettingsIO;
import net.dv8tion.jda.core.EmbedBuilder;

public class SetChannelCommand extends Command {
	
	public SetChannelCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("SetChannel", CommandIDs.SET_CHANNEL_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		executer.setGuildChannel(lastInput.getLastEvent().getChannel().getName(), lastInput.getLastEvent());
		SettingsIO.saveSettings(executer);
	}
	
	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "SetChannel command verlangt Parameter (@Param: Name)", false)
			.build(), lastInput.getLastEvent());
	}
}
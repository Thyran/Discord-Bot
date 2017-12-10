package Bots.Commands.ChannelCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShowChannelsCommand extends Command {

	public ShowChannelsCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShowChannels", CommandIDs.SHOW_CHANNELS_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		for (String channel : executer.guildChannels) {
			Voids.sendMessageToCurrentChannel(channel, lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Ausgabe Fehler", "Fehler beim Ausgeben der Channels", false)
			.build(), lastInput.getLastEvent());
	}
}

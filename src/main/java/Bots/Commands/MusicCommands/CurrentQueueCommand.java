package Bots.Commands.MusicCommands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Music.MusicCommandInterface;
import net.dv8tion.jda.core.EmbedBuilder;

public class CurrentQueueCommand extends Command {

	public CurrentQueueCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("CurrentQueue", CommandIDs.CURRENT_QUEUE_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (!MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
			int sideNumb = lastInput.getLastInput().length;
			
			List<String> tracks = new ArrayList<String>();
			List<String> trackSublist;
			
			MusicCommandInterface.musicManager.getManager(lastInput.getLastEvent().getGuild())
				.getQueue().forEach(audioInfo -> tracks.add(MusicCommandInterface.musicManager.buildQueueMessage(audioInfo)));
			if (tracks.size() > 20) {
				trackSublist = tracks.subList((sideNumb -1)*20, (sideNumb -1)*20 + 20);
			} else {
				trackSublist = tracks;
			}
			
			String out = trackSublist.stream().collect(Collectors.joining("\n"));
			int sideNumbAll = tracks.size() >= 20 ? tracks.size() / 20 : 1;
			
			Voids.sendMessageToCurrentChannel(new EmbedBuilder()
				.setDescription(
						"**CURRENT QUEUE:**\n" +
						"*[" + MusicCommandInterface.musicManager.getManager(lastInput.getLastEvent().getGuild()).getQueue().stream() +
						" Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*\n\n" +
						out)
				.build(), lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler beim Anzeigen der aktuellen Queue", false)
			.build(), lastInput.getLastEvent());
	}
}

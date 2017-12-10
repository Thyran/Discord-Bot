package Bots.Commands.MusicCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Music.MusicCommandInterface;
import net.dv8tion.jda.core.EmbedBuilder;

public class StopTrackCommand extends Command {

	public StopTrackCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("StopTrack", CommandIDs.STOP_TRACK_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (!MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
			MusicCommandInterface.musicManager.getManager(lastInput.getLastEvent().getGuild()).clearQueue();
			MusicCommandInterface.musicManager.stop(lastInput.getLastEvent().getGuild());
			lastInput.getLastEvent().getGuild().getAudioManager().closeAudioConnection();
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler beim stoppen des Tracks", false)
			.build(), lastInput.getLastEvent());
	}
}

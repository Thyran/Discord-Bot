package Bots.Commands.MusicCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandArchive;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandExecution;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Music.MusicCommandInterface;
import net.dv8tion.jda.core.EmbedBuilder;

public class PlayTrackCommand extends Command {

	public PlayTrackCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("PlayTrack", CommandIDs.PLAY_TRACK_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		String track = lastInput.getLastInput()[1];
		if (!(track.startsWith("https://") || track.startsWith("http://")))
			track = "ytsearch: " + track;
		
		MusicCommandInterface.lastTrack = track;
		MusicCommandInterface.musicManager.loadTrack(track, lastInput.getLastEvent().getMember());
		
		try {
			Thread.sleep(1200);
			executer.appendCommandExecution(new CommandExecution(new Input().setLastEvent(null).setLastEvent(lastInput.getLastEvent())
				, CommandArchive.currentTrackCommand));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "Play command verlangt Parameter (@Param: Track)", false)
			.build(), lastInput.getLastEvent());
	}

}

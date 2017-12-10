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

public class LastTrackCommand extends Command {
	
	public LastTrackCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("LastTrack", CommandIDs.LAST_TRACK_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
			if (!MusicCommandInterface.lastTrack.equals("")) {
				MusicCommandInterface.musicManager.loadTrack(MusicCommandInterface.lastTrack, lastInput.getLastEvent().getMember());
				
				try {
					Thread.sleep(1000);
					executer.appendCommandExecution(new CommandExecution(new Input().setLastEvent(null).setLastEvent(lastInput.getLastEvent())
						, CommandArchive.currentTrackCommand));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				Voids.sendMessageToCurrentChannel(new EmbedBuilder()
					.setColor(Color.RED)
					.setDescription("**Fehler**")
					.addField("Musik Fehler", "LastTrack command verlangt einen Track (benutze " + 
						CommandArchive.playTrackCommand.getName() + " zuerst)", false)
					.build(), lastInput.getLastEvent());
			}
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler beim Spielen des letzten Tracks", false)
			.build(), lastInput.getLastEvent());
	}
}

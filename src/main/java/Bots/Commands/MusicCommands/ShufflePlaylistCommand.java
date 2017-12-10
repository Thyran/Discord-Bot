package Bots.Commands.MusicCommands;

import java.awt.Color;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Music.MusicCommandInterface;
import net.dv8tion.jda.core.EmbedBuilder;

public class ShufflePlaylistCommand extends Command {

	public ShufflePlaylistCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("ShufflePlaylist", CommandIDs.SHUFFLE_PLAYLIST_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (!MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
			MusicCommandInterface.musicManager.getManager(lastInput.getLastEvent().getGuild()).shuffleQueue();
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler bei Shufflen der Playlist", false)
			.build(), lastInput.getLastEvent());
	}
}

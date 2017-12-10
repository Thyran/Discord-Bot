package Bots.Commands.MusicCommands;

import java.awt.Color;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import Bots.utils.Music.MusicCommandInterface;
import net.dv8tion.jda.core.EmbedBuilder;

public class CurrentTrackCommand extends Command {

	public CurrentTrackCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("CurrentTrack", CommandIDs.CURRENT_TRACK_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (!MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
			AudioTrack track = MusicCommandInterface.musicManager.getPlayer(lastInput.getLastEvent().getGuild()).getPlayingTrack();
			AudioTrackInfo info = track.getInfo();
			
			Voids.sendMessageToCurrentChannel(new EmbedBuilder()
				.setColor(Color.ORANGE)
				.setDescription(":headphones: **CURRENT TRACK INFO:**")
				.addField(":cd: Title", info.title, false)
				.addField(":stopwatch: Duration", "`[ " + MusicCommandInterface.musicManager.getTimestamp(track.getPosition()) + "/ " + 
					MusicCommandInterface.musicManager.getTimestamp(track.getDuration()) + " ]`", false)
				.addField(":musical_keyboard: Author", info.author, false)
				.build(), lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler beim Anzeigen des aktuellen Tracks", false)
			.build(), lastInput.getLastEvent());
	}
}

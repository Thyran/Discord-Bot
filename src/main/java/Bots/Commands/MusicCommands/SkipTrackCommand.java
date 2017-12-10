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

public class SkipTrackCommand extends Command {

	public SkipTrackCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("SkipTrack", CommandIDs.SKIP_TRACK_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		if (lastInput.getLastEvent().getMember().getVoiceState().getChannel()
				.equals(MusicCommandInterface.musicManager.getChannel(lastInput.getLastEvent().getGuild()))) {
			String message = "";
			if (!MusicCommandInterface.skipVotedMembers.contains(lastInput.getLastEvent().getMember())) {
				MusicCommandInterface.skipVotedMembers.add(lastInput.getLastEvent().getMember());
				message = "Hat für einen Skip gevoted";
			} else {
				message = "Hat bereits gevoted";
			}
			Voids.sendMessageToCurrentChannel(new EmbedBuilder()
				.setColor(Color.GREEN)
				.setDescription("**SkipVoteInfo**")
				.addField("Voter", lastInput.getLastEvent().getAuthor().getName(), false)
				.addField("Status", message, false)
				.addField("Votes", new Integer(MusicCommandInterface.skipVotedMembers.size()).toString() + "/" +
					new Integer(MusicCommandInterface.musicManager.getChannel(lastInput.getLastEvent()
						.getGuild()).getMembers().size() -1).toString(), false)
				.build(), lastInput.getLastEvent());
			
			if (MusicCommandInterface.skipVotedMembers.size() >= ((MusicCommandInterface.musicManager
					.getChannel(lastInput.getLastEvent().getGuild()).getMembers().size() -1) / 2)) {
				if (!MusicCommandInterface.musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
					MusicCommandInterface.musicManager.skip(lastInput.getLastEvent().getGuild());
					MusicCommandInterface.skipVotedMembers.clear();
					if (!MusicCommandInterface.musicManager.getManager(lastInput.getLastEvent().getGuild()).getQueue().isEmpty()) {
						try {
							Thread.sleep(1000);
							executer.appendCommandExecution(new CommandExecution(lastInput, CommandArchive.currentTrackCommand));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			Voids.sendMessageToCurrentChannel(new EmbedBuilder()
				.setColor(Color.RED)
				.setDescription("**Fehler**")
				.addField("Musik Fehler", "In diesem Kanal wird zur Zeit keine Musik gespielt", false)
				.build(), lastInput.getLastEvent());
		}
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Musik Fehler", "Fehler beim Skippen des Tracks", false)
			.build(), lastInput.getLastEvent());
	}

}

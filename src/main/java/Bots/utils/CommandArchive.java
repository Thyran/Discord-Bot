package Bots.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class CommandArchive {
	private static MusicManager musicManager = new MusicManager();
	private static String lastTrack = "";
	private static List<Member> skipVotedMembers = new ArrayList<Member>();
	
	public static final Command rerollCommand = new Command("Roll", 0, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String[] names = lastInput.getLastInput()[1].split(",");
		
			Random rn = new Random();
			int number = rn.nextInt(names.length);
		
			Voids.sendMessageToCurrentChannel(names[number] + " was chosen by the power of coincidence", lastInput.getLastEvent());
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Roll command needs parameter (@Param: List of Players)", lastInput.getLastEvent());
		}
	});
	
	public static final Command setChannelCommand = new Command("SetChannel", 1, true, false, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.setGuildChannel(lastInput.getLastEvent().getChannel().getName(), lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("SetChannel command needs Parameter (@Param: Name)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addRolePermissionCommand = new Command("AddRolePermission", 2, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddRolePermission command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command addUserPermissionCommand = new Command("AddUserPermission", 3, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command runCommand = new Command("Run", 4, false, false, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.addPermittedUser(lastInput.getLastEvent().getGuild().getOwner().getUser().getName(), lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			System.out.println("Fehler beim Start");
		}
	});
	
	public static final Command removeRolePermissionCommand = new Command("RemoveRolePermission", 5, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedRole(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveRolePermission command needs Parameter (@Param: RoleName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command removeUserPermissionCommand = new Command("RemoveUserPermission", 6, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			executer.removePermittedUser(lastInput.getLastInput()[1], lastInput.getLastEvent());
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveUserPermission command needs Parameter (@Param: UserName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command loadSettingsCommand = new Command("LoadSettings", 7, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			SettingsIO.loadSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Laden der Einstellungen", lastInput.getLastEvent());
		}
	});
	
	public static final Command saveSettingsCommand = new Command("SaveSettings", 8, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Speichern der Einstellungen", lastInput.getLastEvent());
		}
	});
	
	public static final Command showPermittedUsersCommand = new Command("ShowPermittedUsers", 9, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String user : executer.permittedUsers) {
				Voids.sendMessageToCurrentChannel(user, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der User", lastInput.getLastEvent());
		}
	});
	
	public static final Command showPermittedRolesCommand = new Command("ShowPermittedRoles", 10, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String role : executer.permittedRoles) {
				Voids.sendMessageToCurrentChannel(role, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Rollen", lastInput.getLastEvent());
		}
	});
	
	public static final Command showChannelsCommand = new Command("ShowChannels", 11, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (String channel : executer.guildChannels) {
				Voids.sendMessageToCurrentChannel(channel, lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Channels", lastInput.getLastEvent());
		}
	});
	
	public static final Command addUseCommand = new Command("AddUse", 12, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			String use = lastInput.getLastInput()[2];
			
			Commands.addUseToCommand(Commands.getCommandID(command), use);
			SettingsIO.saveSettings(executer);
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("AddUse command needs Paramters (@Param: commandName, use)", lastInput.getLastEvent());
		}
	});
	
	public static final Command removeUseCommand = new Command("RemoveUse", 13, true, true, true, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			String use = lastInput.getLastInput()[2];
			
			if (!(command.equals(addUseCommand.getName()))) {
				Commands.removeUseFromCommand(Commands.getCommandID(command), use);
				SettingsIO.saveSettings(executer);
			} else {
				Voids.sendMessageToCurrentChannel("Du kannst das AddUse command nicht bearbeiten", lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("RemoveUse command needs Paramters (@Param: commandName, use)", lastInput.getLastEvent());
		}
	});
	
	public static final Command showCommandsCommand = new Command("ShowCommands", 14, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			for (Command c : Commands.commands) {
				Voids.sendMessageToCurrentChannel(c.getName(), lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Ausgeben der Commands", lastInput.getLastEvent());
		}
	});
	
	public static final Command showCommandUsesCommand = new Command("ShowUses", 15, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String command = lastInput.getLastInput()[1];
			
			for (Command c : Commands.commands) {
				if (c.getCommand() == Commands.getCommandID(command)) {
					for (String key : Commands.keys) {
						if (Commands.isUseInCommand(c, key)) {
							Voids.sendMessageToCurrentChannel(key, lastInput.getLastEvent());
						}
					}
				}
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("ShowUse command needs Parameters (@Param: commandName)", lastInput.getLastEvent());
		}
	});
	
	public static final Command playTrackCommand = new Command("PlayTrack", 16, false, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			String track = lastInput.getLastInput()[1];
			if (!(track.startsWith("https://") || track.startsWith("http://")))
				track = "ytsearch: " + track;
			
			lastTrack = track;
			musicManager.loadTrack(track, lastInput.getLastEvent().getMember());
			
			try {
				Thread.sleep(1000);
				executer.appendCommandExecution(new CommandExecution(new Input().setLastEvent(null).setLastEvent(lastInput.getLastEvent()), currentTrackCommand));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void onError(Input lastInput) {
				Voids.sendMessageToCurrentChannel("Play command needs Paramters (@Param: track)", lastInput.getLastEvent());
		}
	});
	
	public static final Command skipTrackCommand = new Command("SkipTrack", 17, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (lastInput.getLastEvent().getMember().getVoiceState().getChannel().equals(musicManager.getChannel(lastInput.getLastEvent().getGuild()))) {
				if (!skipVotedMembers.contains(lastInput.getLastEvent().getMember())) {
					skipVotedMembers.add(lastInput.getLastEvent().getMember());
					Voids.sendMessageToCurrentChannel(lastInput.getLastEvent().getAuthor().getName() + " hat für einen Skip gevoted", lastInput.getLastEvent());
				} else {
					Voids.sendMessageToCurrentChannel(lastInput.getLastEvent().getAuthor().getName() + ", du hast bereits gevoted", lastInput.getLastEvent());
				}
				Voids.sendMessageToCurrentChannel("Aktueller Stand: " + 
					new Integer(skipVotedMembers.size()).toString() + "/" + 
					new Integer(musicManager.getChannel(lastInput.getLastEvent().getGuild()).getMembers().size() -1).toString(), 
					lastInput.getLastEvent());
				
				if (skipVotedMembers.size() >= ((musicManager.getChannel(lastInput.getLastEvent().getGuild()).getMembers().size() -1) / 2)) {
					if (!musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
						musicManager.skip(lastInput.getLastEvent().getGuild());
						skipVotedMembers.clear();
					}
				}
			} else {
				Voids.sendMessageToCurrentChannel("In diesem Channel wird zur Zeit keine Musik gespielt", lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim skippen des Tracks", lastInput.getLastEvent());
		}
	});
	
	public static final Command stopTrackCommand = new Command("StopTrack", 18, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (!musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
				musicManager.getManager(lastInput.getLastEvent().getGuild()).clearQueue();
				musicManager.skip(lastInput.getLastEvent().getGuild());
				lastInput.getLastEvent().getGuild().getAudioManager().closeAudioConnection();
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim stoppen des Tracks", lastInput.getLastEvent());
		}
	});
	
	public static final Command shufflePlaylistCommand = new Command("ShufflePlaylist", 19, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (!musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
				musicManager.getManager(lastInput.getLastEvent().getGuild()).shuffleQueue();
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim shufflen der Playlist", lastInput.getLastEvent());
		}
	});
	
	public static final Command currentTrackCommand = new Command("CurrentTrack", 20, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (!musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
				AudioTrack track = musicManager.getPlayer(lastInput.getLastEvent().getGuild()).getPlayingTrack();
				AudioTrackInfo info = track.getInfo();
				
				Voids.sendMessageToCurrentChannel(new EmbedBuilder()
					.setDescription("**CURRENT TRACK INFO:**")
					.addField("Title", info.title, false)
					.addField("Duration", "`[ " + musicManager.getTimestamp(track.getPosition()) + "/ " + musicManager.getTimestamp(track.getDuration()) + " ]`", false)
					.addField("Author", info.author, false)
					.build(), lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Anzeigen des aktuellen Tracks", lastInput.getLastEvent());
		}
	});
	
	public static final Command currentQueueCommand = new Command("CurrentQueue", 21, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (!musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
				int sideNumb = lastInput.getLastInput().length;
				
				List<String> tracks = new ArrayList<String>();
				List<String> trackSublist;
				
				musicManager.getManager(lastInput.getLastEvent().getGuild()).getQueue().forEach(audioInfo -> tracks.add(musicManager.buildQueueMessage(audioInfo)));
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
							"*[" + musicManager.getManager(lastInput.getLastEvent().getGuild()).getQueue().stream() +
							" Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*\n\n" +
							out)
					.build(), lastInput.getLastEvent());
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim Anzeigen der Aktuellen Queue", lastInput.getLastEvent());
		}
	});
	
	public static final Command lastTrackCommand = new Command("LastTrack", 22, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			if (musicManager.isIdle(lastInput.getLastEvent().getGuild())) {
				if (!lastTrack.equals("")) {
					musicManager.loadTrack(lastTrack, lastInput.getLastEvent().getMember());
					
					try {
						Thread.sleep(1000);
						executer.appendCommandExecution(new CommandExecution(new Input().setLastEvent(null).setLastEvent(lastInput.getLastEvent()), currentTrackCommand));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					Voids.sendMessageToCurrentChannel("ResumeTrack command needs a Track first (use Play command)", lastInput.getLastEvent());
				}
			}
		}
		
		public void onError(Input lastInput) {
			Voids.sendMessageToCurrentChannel("Fehler beim spielen des letzten Tracks", lastInput.getLastEvent());
		}
	});
	
	public static final Command testCommand = new Command("Test", 100, true, true, false, new Execution() {
		public void onExecution(Input lastInput, CommandExecuter executer) {
			
			
		}
		
		public void onError(Input lastInput) {
			
		}
	});
}
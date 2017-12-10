package Bots.utils.Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Bots.Commands.RollCommand;
import Bots.Commands.ShowCommandsCommand;
import Bots.Commands.TestCommand;
import Bots.Commands.ChannelCommands.SetChannelCommand;
import Bots.Commands.ChannelCommands.ShowChannelsCommand;
import Bots.Commands.MusicCommands.CurrentQueueCommand;
import Bots.Commands.MusicCommands.CurrentTrackCommand;
import Bots.Commands.MusicCommands.LastTrackCommand;
import Bots.Commands.MusicCommands.PlayTrackCommand;
import Bots.Commands.MusicCommands.ShufflePlaylistCommand;
import Bots.Commands.MusicCommands.SkipTrackCommand;
import Bots.Commands.MusicCommands.StopTrackCommand;
import Bots.Commands.PermissionCommands.AddRolePermissionCommand;
import Bots.Commands.PermissionCommands.AddUserPermissionCommand;
import Bots.Commands.PermissionCommands.RemoveRolePermissionCommand;
import Bots.Commands.PermissionCommands.RemoveUserPermissionCommand;
import Bots.Commands.PermissionCommands.RunCommand;
import Bots.Commands.PermissionCommands.ShowPermittedRolesCommand;
import Bots.Commands.PermissionCommands.ShowPermittedUsersCommand;
import Bots.Commands.SettingsCommands.LoadSettingsCommand;
import Bots.Commands.SettingsCommands.SaveSettingsCommand;
import Bots.Commands.UsageCommands.AddUseCommand;
import Bots.Commands.UsageCommands.RemoveUseCommand;
import Bots.Commands.UsageCommands.ShowCommandUsesCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class CommandArchive {
	
	public static final Command rollCommand = new RollCommand(true, true, false);
	public static final Command setChannelCommand = new SetChannelCommand(true, true, true);
	public static final Command addRolePermissionCommand = new AddRolePermissionCommand(true, true, true);
	public static final Command addUserPermissionCommand = new AddUserPermissionCommand(true, true, true);
	public static final Command runCommand = new RunCommand(false, false, false);
	public static final Command removeRolePermissionCommand = new RemoveRolePermissionCommand(true, true, true);
	public static final Command removeUserPermissionCommand = new RemoveUserPermissionCommand(true, true, true);
	public static final Command loadSettingsCommand = new LoadSettingsCommand(true, true, true);
	public static final Command saveSettingsCommand = new SaveSettingsCommand(true, true, true);
	public static final Command showPermittedUsersCommand = new ShowPermittedUsersCommand(true, true, true);
	public static final Command showPermittedRolesCommand = new ShowPermittedRolesCommand(true, true, true);
	public static final Command showChannelsCommand = new ShowChannelsCommand(true, true, true);
	public static final Command addUseCommand = new AddUseCommand(true, true, true);
	public static final Command removeUseCommand = new RemoveUseCommand(true, true, true);
	public static final Command showCommandsCommand = new ShowCommandsCommand(true, true, false);
	public static final Command showCommandUsesCommand = new ShowCommandUsesCommand(true, true, false);
	public static final Command playTrackCommand = new PlayTrackCommand(false, true, false);
	public static final Command skipTrackCommand = new SkipTrackCommand(false, true, false);
	public static final Command stopTrackCommand = new StopTrackCommand(false, true, false);
	public static final Command shufflePlaylistCommand = new ShufflePlaylistCommand(false, true, false);
	public static final Command currentTrackCommand = new CurrentTrackCommand(false, true, false);
	public static final Command currentQueueCommand = new CurrentQueueCommand(false, true, false);
	public static final Command lastTrackCommand = new LastTrackCommand(false, true, false);
	public static final Command testCommand = new TestCommand(true, true, false);
}
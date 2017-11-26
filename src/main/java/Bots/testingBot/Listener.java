package Bots.testingBot;

import java.util.Random;

import Bots.utils.Commands;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {
	
	private void sendMessageToPrivateChannel(String message, PrivateMessageReceivedEvent event) {
		if (!event.getAuthor().isBot())
			event.getChannel().sendMessage(message).queue();
	}
	
	private void sendMessageToCurrentChannel(String message, GuildMessageReceivedEvent event) {
		if (!event.getAuthor().isBot())
			event.getChannel().sendMessage(message).queue();
	}
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String command = event.getMessage().getContent();
		
		if (command.charAt(0) == '-') {
			command = command.substring(1);
			String[] commandParams = command.split(" ");
			
			String message = "";
			try {
				switch((Integer)Commands.map.get(commandParams[0])) {
				case 0:	
					sendMessageToCurrentChannel("executing command: Roll", event);
					
					try {
						String[] names = commandParams[1].split(",");
						
						Random rn = new Random();
						int number = rn.nextInt(names.length);
						
						sendMessageToCurrentChannel(names[number] + " was chosen by the power of coincidence", event);
						
					} catch (ArrayIndexOutOfBoundsException e) {
						sendMessageToCurrentChannel("Roll command needs parameters", event);
					}
					break;
				case 1:
					sendMessageToCurrentChannel("executing command: Play", event);
					break;
				default:
					sendMessageToCurrentChannel("command not known: " + command, event);
					break;
				}
			}
			catch (NullPointerException e) {
				sendMessageToCurrentChannel("command not known: " + command, event);
			}
		}
	}
}
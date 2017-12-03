package Bots.utils;

public class Settings {
	
	private static String channel = "";
	
	public static boolean isChannelSet() { return (channel != ""); }
	public static String getChannel() { return (channel.equals("") ? null : channel); }
	
	public static void setChannel(String channelName) {
		channel = channelName;
	}
}
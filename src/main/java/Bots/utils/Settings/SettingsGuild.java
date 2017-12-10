package Bots.utils.Settings;

import java.util.ArrayList;

public class SettingsGuild {
	public String name = "";
	public int id = -1;
	public String channel = "";
	public ArrayList<String> users = null;
	public ArrayList<String> roles = null;
	
	public SettingsGuild(String name, int id) {
		this.name = name;
		this.id = id;
		users = new ArrayList<String>();
		roles = new ArrayList<String>();
	}
	
	public void addUser(String user) {
		users.add(user);
	}
	
	public void addRole(String role) {
		roles.add(role);
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
}

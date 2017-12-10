package Bots.utils.Settings;

import java.util.ArrayList;

public class SettingsCommand {
	public String commandName = "";
	public int commandID = -1;
	public ArrayList<String> uses = null;
	
	public SettingsCommand(String commandName, int commandID) {
		this.commandName = commandName;
		this.commandID = commandID;
		
		uses = new ArrayList<String>();
	}
	
	public void addUse(String use) {
		uses.add(use);
	}
}
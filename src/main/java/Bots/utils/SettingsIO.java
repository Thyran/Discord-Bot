package Bots.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SettingsIO {
	
	private static String guildName = "";
	private static String guildChannel = "";
	private static String userName = "";
	private static String roleName = "";
	private static String commandName = "";
	private static String commandID = "";
	
	public static void loadSettings(CommandExecuter executer) {
		try {
			executer.permittedUsers.clear();
			executer.permittedRoles.clear();
			
			File fXmlFile = new File("../testingBot/src/main/Settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), executer);
			}

	    } catch (Exception e) {
	    	System.out.println("Fehler beim Laden der Einstellungen");
	    }
	}

	private static void printNote(NodeList nodeList, CommandExecuter executer) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tempNode.getNodeName().equals("user") && tempNode.getParentNode().getParentNode().getParentNode().getNodeName().equals("guilds")) {
					userName = tempNode.getTextContent();
					executer.addPermittedUser(userName, guildName);
				} else if (tempNode.getNodeName().equals("role") && tempNode.getParentNode().getParentNode().getParentNode().getNodeName().equals("guilds")) {
					roleName = tempNode.getTextContent();
					executer.addPermittedRole(roleName, guildName);
				} else if (tempNode.getNodeName().equals("name") && tempNode.getParentNode().getParentNode().getNodeName().equals("guilds")) {
					guildName = tempNode.getTextContent();
				} else if (tempNode.getNodeName().equals("name") && tempNode.getParentNode().getParentNode().getNodeName().equals("commands")) {
					commandName = tempNode.getTextContent();
				} else if (tempNode.getNodeName().equals("channel") && tempNode.getParentNode().getParentNode().getNodeName().equals("guilds")) {
					guildChannel = tempNode.getTextContent();
					executer.setGuildChannel(guildChannel, guildName);
				} else if (tempNode.getNodeName().equals("use") && tempNode.getParentNode().getParentNode().getParentNode().getNodeName().equals("commands")) {
					Commands.addUseToCommand(new Integer(commandID), tempNode.getTextContent());
				} else if (tempNode.getNodeName().equals("id") && tempNode.getParentNode().getParentNode().getNodeName().equals("commands")) {
					commandID = tempNode.getTextContent();
				} else if (tempNode.getNodeName().equals("commandChar") && tempNode.getParentNode().getNodeName().equals("commands")) {
					Commands.CommandChar = tempNode.getTextContent().charAt(0);
				}
		
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes(), executer);
				}
			}
	    }
	}
	
	private static void writeLine(BufferedWriter bw, String line) throws IOException {
		bw.newLine();
		bw.write(line);
	}
	
	private static SettingsGuild getGuildInGuilds(ArrayList<SettingsGuild> guilds, String name) {
		SettingsGuild result = null;
		
		for (SettingsGuild guild : guilds) {
			if (guild.name.equals(name)) {
				result = guild;
				break;
			}
		}
		
		return result;
	}
	
	private static boolean isUserInGuild(SettingsGuild guild, String name) {
		boolean result = false;
		
		for (String user : guild.users) {
			if (user.equals(name)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	private static boolean isRoleInGuild(SettingsGuild guild, String name) {
		boolean result = false;
		
		for (String role : guild.roles) {
			if (role.equals(name)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public static void saveSettings(CommandExecuter executer) {
		try {
			File saveFile = new File("../testingBot/src/main/Settings.xml");
			FileWriter fw = new FileWriter(saveFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			ArrayList<SettingsGuild> guilds = new ArrayList<SettingsGuild>();
			for (String s : executer.permittedUsers) {
				String[] params = s.split("/");
				
				SettingsGuild guild = getGuildInGuilds(guilds, params[0]);
				if (guild != null) {
					if (!isUserInGuild(guild, params[1])) {
						guild.addUser(params[1]);
					}
				} else {
					guilds.add(new SettingsGuild(params[0], guilds.size()));
					guilds.get(guilds.size() -1).addUser(params[1]);
				}
			}
			
			for (String s : executer.permittedRoles) {
				String[] params = s.split("/");
				
				SettingsGuild guild = getGuildInGuilds(guilds, params[0]);
				if (guild != null) {
					if (!isRoleInGuild(guild, params[1])) {
						guild.addRole(params[1]);
					}
				} else {
					guilds.add(new SettingsGuild(params[0], guilds.size()));
					guilds.get(guilds.size() -1).addRole(params[1]);
				}
			}
			
			for (String s : executer.guildChannels) {
				String[] params = s.split("/");
				
				SettingsGuild guild = getGuildInGuilds(guilds, params[0]);
				if (guild != null) {
					guild.setChannel(params[1]);
				} else {
					guilds.add(new SettingsGuild(params[0], guilds.size()));
					guilds.get(guilds.size() -1).setChannel(params[1]);
				}
			}
			
			ArrayList<SettingsCommand> commands = new ArrayList<SettingsCommand>();
			for (Command c : Commands.commands) {
				commands.add(new SettingsCommand(c.getName(), c.getCommand()));
				for (String s : Commands.keys) {
					if (((Command)Commands.map.get(s)).getCommand() == c.getCommand())
						commands.get(commands.size() -1).addUse(s);
				}
			}
			
			if (!guilds.isEmpty()) {
				bw.flush();
				bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				
				writeLine(bw, "<settings>");
				writeLine(bw, "\t" + "<commands>");
				writeLine(bw, "\t\t" + "<commandChar>" + Commands.CommandChar + "</commandChar>");
				for (SettingsCommand command : commands) {
					writeLine(bw, "\t\t" + "<command id=\"" + new Integer(command.commandID).toString() + "\">");
					writeLine(bw, "\t\t\t" + "<name>" + command.commandName + "Command" + "</name>");
					writeLine(bw, "\t\t\t" + "<id>" + new Integer(command.commandID).toString() + "</id>");
					writeLine(bw, "\t\t\t" + "<uses>");
					for (String use : command.uses) {
						writeLine(bw, "\t\t\t\t" + "<use>" + use + "</use>");
					}
					writeLine(bw, "\t\t\t" + "</uses>");
					writeLine(bw, "\t\t" + "</command>");
				}
				writeLine(bw, "\t" + "</commands>");
				writeLine(bw, "\t" + "<guilds>");	
				for (SettingsGuild guild : guilds) {
					writeLine(bw, "\t\t" + "<guild id=\"" + new Integer(guild.id).toString() + "\">");
					writeLine(bw, "\t\t\t" + "<name>" + guild.name + "</name>");
					writeLine(bw, "\t\t\t" + "<channel>" + guild.channel + "</channel>");
					writeLine(bw, "\t\t\t" + "<permittedUsers>");
					for (String user : guild.users) {
						writeLine(bw, "\t\t\t\t" + "<user>" + user + "</user>");
					}
					writeLine(bw, "\t\t\t" + "</permittedUsers>");
					writeLine(bw, "\t\t\t" + "<permittedRoles>");
					for (String role : guild.roles) {
						writeLine(bw, "\t\t\t\t" + "<role>" + role + "</role>");
					}
					writeLine(bw, "\t\t\t" + "</permittedRoles>");
					writeLine(bw, "\t\t" + "</guild>");
				}
				writeLine(bw, "\t" + "</guilds>");
				writeLine(bw, "</settings>");
			}
			
			bw.close();
			
		} catch (IOException e) {
			System.out.print("Fehler beim Speichern der Einstellungen");
		}
	}
}
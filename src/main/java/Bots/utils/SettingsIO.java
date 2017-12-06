package Bots.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SettingsIO {
	
	private static String guildName = "";
	private static String userName = "";
	private static String roleName = "";
	
	public static void loadSettings(CommandExecuter executer) {
		try {
			executer.permittedUsers.clear();
			executer.permittedRoles.clear();
			
			File fXmlFile = new File("../testingBot/src/main/Settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {
				printNote(doc.getChildNodes(), executer);
			}

	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	}

	private static void printNote(NodeList nodeList, CommandExecuter executer) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				//System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				//System.out.println("Node Value =" + tempNode.getTextContent());
				if (tempNode.getNodeName() == "user") {
					userName = tempNode.getTextContent();
					executer.addPermittedUser(userName, guildName);
				}
				else if (tempNode.getNodeName() == "role") {
					roleName = tempNode.getTextContent();
					executer.addPermittedRole(roleName, guildName);
				}
				else if (tempNode.getNodeName() == "name")
					guildName = tempNode.getTextContent();
				
				/*if (tempNode.hasAttributes()) {
					NamedNodeMap nodeMap = tempNode.getAttributes();
		
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}
				}*/
		
				if (tempNode.hasChildNodes()) {
					printNote(tempNode.getChildNodes(), executer);
				}
				//System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
	    }
	}
	
	public static void saveSettings(CommandExecuter executer) {
		
	}
}
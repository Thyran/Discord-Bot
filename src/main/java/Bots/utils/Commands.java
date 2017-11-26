package Bots.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Commands {
	
	public static HashMap map = new HashMap();
	
	public static final char CommandChar = '-';
	
	public static void init() {
		
		try {
			loadCommands();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadCommands() throws IOException {
		
		FileReader fr = new FileReader("../testingBot/src/main/Commands.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String currentLine;
		while((currentLine = br.readLine()) != null && currentLine.length() != 0) {
			String[] args = currentLine.split(" ");
			
			map.put(args[0], new Integer(args[2]));
		}
		
		br.close();
	}
}
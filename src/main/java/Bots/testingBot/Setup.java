package Bots.testingBot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Setup {
	public static String loadToken() {
		String result = "";
		try {
			FileReader fr = new FileReader("../testingBot/Token.txt");
			BufferedReader br = new BufferedReader(fr);
			
			result = br.readLine();
			
			br.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Laden des Tokens");
		}
		
		return result;
	}
}
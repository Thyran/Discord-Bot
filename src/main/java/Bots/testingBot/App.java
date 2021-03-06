package Bots.testingBot;

import javax.security.auth.login.LoginException;

import Bots.utils.Commands.Commands;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class App {
	
    public static void main( String[] args )
    		throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	
    	Commands.init();
    	
        JDABuilder builder = new JDABuilder(AccountType.BOT).setToken(Setup.loadToken()).setAutoReconnect(true)
        		.setStatus(OnlineStatus.ONLINE).addEventListener(new Listener()).setGame(Game.watching("WapBap"));
        JDA bot = builder.buildBlocking();
    }
}
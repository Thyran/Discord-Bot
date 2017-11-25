package Bots.testingBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class App extends ListenerAdapter {
    public static void main( String[] args ) 
    		throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
    	
        JDA bot = new JDABuilder(AccountType.BOT).setToken("MzgzOTU4MDg3NTcwMTYxNjY0.DPr2sQ.HjPPIMQbClAQ5lbhOD6k7Ur3b8I").buildBlocking();
        bot.addEventListener(new App());
    }
    
    /**
     * Get called when the Bot receives a Message
     * @param MessageReceiveEvent e 
     * 		received MessageEvent
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
    	
    	// setting up variables
    	Message msg = e.getMessage(); // message the bot receives
    	MessageChannel msgChannel = e.getChannel(); // channel from which the message was send
    	User user = e.getAuthor(); // user who sent the message
    	
    	if (msg.getContent().equals("hello")) {
    		msgChannel.sendMessage("Hello, " + user.getAsMention() + "!").queue();
    	}
    }
}
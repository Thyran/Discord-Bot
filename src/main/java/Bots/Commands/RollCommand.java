package Bots.Commands;

import java.awt.Color;
import java.util.Random;

import Bots.utils.Voids;
import Bots.utils.Commands.Command;
import Bots.utils.Commands.CommandExecuter;
import Bots.utils.Commands.CommandIDs;
import Bots.utils.Input.Input;
import net.dv8tion.jda.core.EmbedBuilder;

public class RollCommand extends Command {
	
	public RollCommand(boolean showExecMessage, boolean checkChannel, boolean checkPermission) {
		super("Roll", CommandIDs.ROLL_COMMAND_ID, showExecMessage, checkChannel, checkPermission);
	}

	@Override
	public void onExecution(Input lastInput, CommandExecuter executer) {
		String[] names = lastInput.getLastInput()[1].split(",");
		
		Random rn = new Random();
		int number = rn.nextInt(names.length);
	
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.MAGENTA)
			.setDescription("**Random**")
			.addField(names[number], " wurde durch die Macht des Zufalls auerkoren", true)
			.build(), lastInput.getLastEvent());
	}

	@Override
	public void onError(Input lastInput) {
		Voids.sendMessageToCurrentChannel(new EmbedBuilder()
			.setColor(Color.RED)
			.setDescription("**Fehler**")
			.addField("Parameter Fehler", "Roll command verlangt Parameter (@Param: SpielerListe)", false)
			.build(), lastInput.getLastEvent());
	}

}

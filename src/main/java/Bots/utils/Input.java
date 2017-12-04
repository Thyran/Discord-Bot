package Bots.utils;

public class Input {
	private String[] lastInput = null;
	private InputEvent event = null;
	
	public Input setLastInput(String[] commandParams) {
		lastInput = commandParams;
		return this;
	}
	
	public Input setLastEvent(InputEvent event) {
		this.event = event;
		return this;
	}
	
	public String[] getLastInput() {
		return lastInput;
	}
	
	public InputEvent getLastEvent() {
		return event;
	}
}
package Bots.utils;

public class ExecutionSettings {
	public static boolean checkChannel = true;
	public static boolean permission = false;
	
	public ExecutionSettings setCheckChannel(boolean checkChannel) {
		this.checkChannel = checkChannel;
		return this;
	}
	
	public ExecutionSettings setPermission(boolean permission) {
		this.permission = permission;
		return this;
	}
}
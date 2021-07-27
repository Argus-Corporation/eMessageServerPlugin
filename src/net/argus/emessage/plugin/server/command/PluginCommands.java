package net.argus.emessage.plugin.server.command;

import net.argus.net.server.command.Command;

public class PluginCommands {

	public static final Command PLUGIN = new PluginCommand();
	
	public static final Command ADD_MONITOR = new AddMonitorCommand();
	public static final Command REMOVE_MONITOR = new RemoveMonitorCommand();
	public static final Command LIST_MONITOR = new ListMonitorCommand();
	
	public static void init() {}
	
}

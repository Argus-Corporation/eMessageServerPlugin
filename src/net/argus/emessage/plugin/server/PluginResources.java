package net.argus.emessage.plugin.server;

import net.argus.file.FileSave;

public class PluginResources {
	
	public static final FileSave MONITOR_WHITE_LIST = new FileSave("whitelist", "/", new String[] {"whitelist", "host", "name"});
	
	public static void init() {
		if(!MONITOR_WHITE_LIST.exists())
			MONITOR_WHITE_LIST.createFile();
	};
}

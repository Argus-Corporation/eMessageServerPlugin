package net.argus.emessage.plugin.server;

import java.io.File;

import net.argus.file.FileSave;
import net.argus.plugin.PluginFile;
import net.argus.util.ArrayManager;

public class PluginResources {
	
	public static final FileSave MONITOR_WHITE_LIST = new FileSave("whitelist", "/", new String[] {"whitelist", "host", "name"});
	
	public static final PluginFile ROOM_SAVER = getPluginFile("room_saver", "cjson", "/", false, "{\"rooms\": []}");
	
	public static PluginFile getPluginFile(String fileName, String suff, String path, boolean overwritable, String lines) {
		return new PluginFile(fileName, suff, path, overwritable) {
			
			@Override
			public String[] getLines() {
				return ArrayManager.add(lines.split("\r\n"), new String[] {"\n\r"});
			}
		};
	}
	
	public static PluginFile getPluginFile(File file, boolean overwritable, String lines) {
		return new PluginFile(file, overwritable) {
			
			@Override
			public String[] getLines() {
				return ArrayManager.add(lines.split("\r\n"), new String[] {"\n\r"});
			}
		};
	}
	
	public static void init() {
		if(!MONITOR_WHITE_LIST.exists())
			MONITOR_WHITE_LIST.createFile();
	};
}

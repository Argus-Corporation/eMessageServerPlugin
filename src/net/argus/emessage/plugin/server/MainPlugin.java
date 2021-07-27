package net.argus.emessage.plugin.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.argus.emessage.plugin.server.command.PluginCommands;
import net.argus.monitoring.server.MonitorWhiteList;
import net.argus.plugin.Plugin;
import net.argus.plugin.PluginEvent;
import net.argus.plugin.annotation.PluginInfo;
import net.argus.util.Error;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

@PluginInfo(name = MainPlugin.NAME, pluginId = MainPlugin.PLUGIN_ID, authors = MainPlugin.AUTHOR, description = MainPlugin.DESCRIPTION, version = MainPlugin.VERSION)
public class MainPlugin extends Plugin {
	
	public static final String NAME = "eMessage";
	public static final String PLUGIN_ID = "emessage-server";
	public static final String AUTHOR = "Argus";
	public static final String DESCRIPTION = "eMessage official server plugin.";
	public static final String VERSION = "1.0";

	@Override
	public void preInit(PluginEvent e) {
		PluginResources.init();
		whiteListMonitor();
	}

	@Override
	public void init(PluginEvent e) {
		PluginCommands.init();
	}

	@Override
	public void postInit(PluginEvent e) {
		
	}
	
	private void whiteListMonitor() {
		List<String> monitors = new ArrayList<String>();
		for(int i = 1; i < PluginResources.MONITOR_WHITE_LIST.countLine() + 1; i++)
			monitors.add(PluginResources.MONITOR_WHITE_LIST.getValue(i));
		
		for(String add : monitors)
			try {MonitorWhiteList.addInetAddress(InetAddress.getByName(add));}
			catch(UnknownHostException e) {Error.createErrorFileLog(e); Debug.log("Host \"" + add + "\" doesn't exist", Info.ERROR);}
	}

}

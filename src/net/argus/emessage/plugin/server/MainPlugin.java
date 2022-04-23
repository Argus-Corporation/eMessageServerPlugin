package net.argus.emessage.plugin.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.argus.emessage.plugin.server.command.PluginCommands;
import net.argus.emessage.server.MainServer;
import net.argus.event.net.server.ServerEvent;
import net.argus.event.net.server.ServerListener;
import net.argus.monitoring.server.MonitorWhiteList;
import net.argus.net.server.room.Room;
import net.argus.plugin.Plugin;
import net.argus.plugin.PluginEvent;
import net.argus.plugin.PluginFile;
import net.argus.plugin.annotation.PluginInfo;
import net.argus.util.Error;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

@PluginInfo(name = MainPlugin.NAME, pluginId = MainPlugin.PLUGIN_ID, authors = MainPlugin.AUTHOR,
description = MainPlugin.DESCRIPTION, version = MainPlugin.VERSION, pluginRequested = {MainPlugin.REQUESTED})
public class MainPlugin extends Plugin {
	
	public static final String NAME = "eMessage";
	public static final String PLUGIN_ID = "emessage-server";
	public static final String AUTHOR = "Argus";
	public static final String DESCRIPTION = "eMessage official server plugin.";
	public static final String VERSION = "1.1b";
	
	public static final String REQUESTED = "";

	@Override
	public void preInit(PluginEvent e) {
		PluginResources.init();
		whiteListMonitor();
	}

	@Override
	public void init(PluginEvent e) {
		PluginCommands.init();
		RoomSaver.init();
		
		try {new WebEmessage();}
		catch(IOException e1) {e1.printStackTrace();}
		
		MainServer.addServerListener(getServerListener());
	}

	@Override
	public void postInit(PluginEvent e) {}
	
	@Override
	public List<PluginFile> getPluginFiles() {
		List<PluginFile> files = new ArrayList<PluginFile>();
		files.add(PluginResources.ROOM_SAVER);
		
		return files;
	}
	
	private ServerListener getServerListener() {
		return new ServerListener() {
			
			private boolean inStopping = false;
			
			@Override
			public void userJoin(ServerEvent e) {}
			
			@Override
			public void stop(ServerEvent e) {}
			
			@Override
			public void stopAction(ServerEvent e) {
				inStopping = true;
			}
			
			@Override
			public void roomRemove(ServerEvent e) {
				if(!inStopping)
					RoomSaver.remove((Room) e.getObject());
			}
			
			@Override
			public void roomCreate(ServerEvent e) {
				RoomSaver.save((Room) e.getObject());
			}
			
			@Override
			public void open(ServerEvent e) {
				
			}
			
			@Override
			public void error(ServerEvent e) {
				
			}
		};
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

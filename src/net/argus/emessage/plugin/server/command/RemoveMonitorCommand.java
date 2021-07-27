package net.argus.emessage.plugin.server.command;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.argus.emessage.plugin.server.PluginResources;
import net.argus.monitoring.server.MonitorWhiteList;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.Command;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class RemoveMonitorCommand extends Command {

	public RemoveMonitorCommand() {
		super("removemonitor", new Structure().add("host"), 10);
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		InetAddress address = null;
		
		try {address = InetAddress.getByName(com.get(0).toString());}
		catch (UnknownHostException e) {
			Debug.log("Host \"" + com.get(0) + "\" doesn't exist", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Host \"" + com.get(0) + "\" doesn't exist"));
			return;
		}
		
		if(!MonitorWhiteList.contains(address)) {
			Debug.log("Host \"" + com.get(0) + "\" is not registered", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Host \"" + com.get(0) + "\" is not registered"));
			return;
		}
				
		MonitorWhiteList.removeInetAddress(address);
		if(PluginResources.MONITOR_WHITE_LIST.deleteValue(address.getHostName()) == -1)
			PluginResources.MONITOR_WHITE_LIST.deleteValue(address.getHostAddress());

		process.send(PackagePrefab.genSystemPackage("Host \"" + address + "\" was removed to the whitelist"));
	}

}

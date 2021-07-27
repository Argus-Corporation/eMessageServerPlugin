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

public class AddMonitorCommand extends Command {

	public AddMonitorCommand() {
		super("addmonitor", new Structure().add("host"), 10);
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
		
		if(MonitorWhiteList.contains(address)) {
			Debug.log("Host \"" + com.get(0) + "\" is already registered", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Host \"" + com.get(0) + "\" is already registered"));
			return;
		}
		
		MonitorWhiteList.addInetAddress(address);
		PluginResources.MONITOR_WHITE_LIST.addValue(address.getHostName());
		
		process.send(PackagePrefab.genSystemPackage("Host \"" + address + "\" was added to the whitelist"));
	}

}

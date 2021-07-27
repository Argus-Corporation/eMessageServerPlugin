package net.argus.emessage.plugin.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.Command;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.plugin.PluginRegister;
import net.argus.plugin.annotation.PluginInfo;

public class PluginCommand extends Command {

	public PluginCommand() {
		super("plugin");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		List<PluginInfo> plugins = PluginRegister.getInfos();
		List<String> pluginNames = new ArrayList<String>();
		
		for(PluginInfo plug : plugins)
			pluginNames.add(plug.name());
		
		process.send(PackagePrefab.genInfoPackage((String[]) pluginNames.toArray(new String[pluginNames.size()])));
	}

}

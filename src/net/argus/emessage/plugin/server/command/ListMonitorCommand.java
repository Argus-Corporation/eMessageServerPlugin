package net.argus.emessage.plugin.server.command;

import java.io.IOException;

import net.argus.monitoring.server.MonitorWhiteList;
import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.Command;
import net.argus.net.server.command.structure.StructuredCommand;

public class ListMonitorCommand extends Command {

	public ListMonitorCommand() {
		super("listmonitor");
	}

	@Override
	protected void run(StructuredCommand com, ServerProcess process) throws IOException {
		process.send(PackagePrefab.genInfoPackage(MonitorWhiteList.getWhiteList()));
	}

}

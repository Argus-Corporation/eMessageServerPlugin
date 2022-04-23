package net.argus.emessage.plugin.server;

import java.io.IOException;

import net.argus.emessage.server.MainServer;
import net.argus.event.net.web.WebListener;
import net.argus.net.socket.WebSocket;
import net.argus.net.web.WebServer;

public class WebEmessage {
	
	private  WebServer webServer;
	
	public WebEmessage() throws IOException {
		webServer = new WebServer();
		webServer.addWebListener(getWebListener());
		webServer.open();
	}
	
	private WebListener getWebListener() {
		return (e) -> {
			try {MainServer.getServer().getConnector().connect(new WebSocket(), e.getSocket());}
			catch (IOException e1) {e1.printStackTrace();}
		};
	}
	
}

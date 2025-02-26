package defaultFeaturePacks.HttpFeaturePack;

import core.ServerCore;
import utilities.Configs;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;
import defaultFeaturePacks.HttpFeaturePack.HTTPRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.function.Consumer;

import com.sun.net.httpserver.*;

import connection_processors.Connection;
import connection_processors.ConnectionProcessor;
public class HTTPRequestProcessor extends ConnectionProcessor {
	private HttpServer server;
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	class ReqHandler implements HttpHandler {
		public Consumer<Connection> coreConnectionsPort;
		public ReqHandler(Consumer<Connection> connPort) {
			this.coreConnectionsPort = connPort;
		}
		public void handle(HttpExchange t) throws IOException {
			this.coreConnectionsPort.accept(new HTTPRequest(t));
		}
   }

	@Override
	public void init(ServerCore core) {
		try {
			this.server = HttpServer.create(new InetSocketAddress(Configs.requestSetting("http.serverPort", Integer.class)), Configs.requestSetting("http.maxIncomingServerConnectionsQueue", Integer.class));
			this.server.createContext("/", new ReqHandler(this.coreConnectionsPort));
			this.server.setExecutor(null);
			this.server.start();
		} catch (IOException e) {
			Logger.log(CommonLogLevels.CRITICAL.level, "Failed to start HTTPServer", Map.of("Exception", e.toString(), "Port", Configs.requestSetting("http.serverPort", Integer.class).toString(), "maxIncomingServerConnectionsQueue", Configs.requestSetting("http.maxIncomingServerConnectionsQueue", Integer.class).toString()));
		}
	}

}
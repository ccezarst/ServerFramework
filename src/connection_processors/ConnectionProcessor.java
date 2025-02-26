package connection_processors;

import java.util.function.Consumer;
import core.ServerCore;

public abstract class ConnectionProcessor implements Runnable{
	public Consumer<Connection> coreConnectionsPort;
	
	protected void sendConnectionToCore(Connection req) {
		this.coreConnectionsPort.accept(req);
	}
	
	public abstract void init(ServerCore core);
}

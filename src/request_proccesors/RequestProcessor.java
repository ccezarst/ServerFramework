package request_proccesors;

import java.util.function.Consumer;
import core.ServerCore;

public abstract class RequestProcessor implements Runnable{
	public Consumer<Connection> coreRequestPort;
	
	protected void sendRequestToCore(Connection req) {
		this.coreRequestPort.accept(req);
	}
	
	public abstract void init(ServerCore core);
}

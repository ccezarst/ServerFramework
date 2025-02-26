package connection_processors;


public abstract class Connection {
	
	public Connection(String ip, ConnectionType connType) {
		this.ip = ip;
		this.connectionType = connType;
	}
	
	public String ip;
	public ConnectionType connectionType;
	
	public abstract void send(Object param);
	public abstract void sendError(String error);
	public abstract Object awaitResponse();
}

package defaultFeaturePacks.HttpFeaturePack;

import java.util.Map;

import request_proccesors.Connection;
import request_proccesors.ConnectionType;
import com.sun.net.httpserver.*;

public class HttpRequest extends Connection{
	private HttpExchange exchange;
	
	public HttpRequest(HttpExchange exchange) {
		super(exchange.getRemoteAddress().toString(), ConnectionType.OneResponse);
		this.method = exchange.getRequestMethod();
		this.url = exchange.getRequestURI().toString();
	}
	
	public String url;
	public String method;
	public Map<String, ?> headers;
	public Map<String, ?> body;
	public Map<String, ?> info;
	@Override
	public void send(Object param) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendError(String error) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object awaitResponse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

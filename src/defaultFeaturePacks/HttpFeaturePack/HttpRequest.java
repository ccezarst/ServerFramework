package defaultFeaturePacks.HttpFeaturePack;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.net.httpserver.*;

import connection_processors.Connection;
import connection_processors.ConnectionType;

public class HTTPRequest extends Connection{
	private HttpExchange exchange;
	
	private Map<String, String[]> copyMap(Headers headers2){
		Map<String, String[]> newMap = new HashMap<>();
		for(Entry<String, List<String>> caca: headers2.entrySet()) {
			newMap.put(caca.getKey(), (String[]) caca.getValue().toArray());
		}
		return newMap;
	}
	
	public HTTPRequest(HttpExchange exchange) {
		super(exchange.getRemoteAddress().toString(), ConnectionType.OneResponse);
		this.method = exchange.getRequestMethod();
		this.url = exchange.getRequestURI().toString();
		this.headers =  this.copyMap(exchange.getRequestHeaders());
		this.contentType = ContentType.getFromHttpName(this.headers.get("Content-Type")[0]);
		//if the user needs a custom body parser they could implement it using a middleware.
	}
	// all content types: https://stackoverflow.com/questions/23714383/what-are-all-the-possible-values-for-http-content-type-header
	public enum ContentType{
		CSS("text/css"),
		PlainText("text/html"),
		JSON("application/json"),
		PDF("application/pdf"),
		XML("application/xml", "text/xml"),
		CSV("text/csv"),
		HTML("text/html"),
		JavaScript("text/javascript");
		public String[] httpNames;
		<T> ContentType(T contentClass, String... httpNames) {
			this.httpNames = httpNames;
		}
		public static ContentType getFromHttpName(String httpName) {
			for(ContentType type : ContentType.values()) {
				for(String name: type.httpNames) {
					if(name == httpName) {
						return type;
					}
				}
			}
			return null;
		}
	}
	
	public String url;
	public String method;
	public Map<String, String[]> headers;
	public InputStream body;
	public ContentType contentType;
	public Map<String, Object> info; // USE OBJECT INSTEAD OF ?
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
		// implement when you use keep alive ig
		return null;
	}
	
	
}

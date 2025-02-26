package defaultFeaturePacks.HttpFeaturePack;

import core.ServerCore;
import request_proccesors.RequestProcessor;

import java.io.IOException;
import java.io.InputStream;

import com.sun.net.httpserver.*;
public class HTTPRequestProcessor extends RequestProcessor {

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	class ReqHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			InputStream is = t.getRequestBody();
			
			
		}
   }

	@Override
	public void init(ServerCore core) {
		// TODO Auto-generated method stub

	}

}
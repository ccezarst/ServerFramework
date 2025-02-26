package defaultFeaturePacks.HttpFeaturePack.RequestParsers;

import java.io.IOException;
import java.util.Map;

import connection_processors.Connection;
import defaultFeaturePacks.HttpFeaturePack.HTTPRequest;
import defaultFeaturePacks.HttpFeaturePack.HTTPRequest.ContentType;
import defaultFeaturePacks.HttpFeaturePack.HTTPUtility;
import middleware_chain.Middleware;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public class HTTPPlainTextBodyParser extends Middleware{

	public HTTPPlainTextBodyParser() {
		super(CommonPriorityLevels.DataFormating);
	}

	@Override
	public Boolean process(Connection c) {
		if(c instanceof HTTPRequest) {
			HTTPRequest req = (HTTPRequest) c;
			if(req.contentType == HTTPRequest.ContentType.PlainText) {
				String result = "";
				try {
					result = HTTPUtility.readFromInputStream(req.body);
				} catch (IOException e) {
					Logger.log(CommonLogLevels.ERROR.level, "Failed to parse request body", Map.of("callerIp", req.ip.toString(), "path", req.url.toString(), "contentType", req.contentType.toString(), "readFromBody", result.toString(), "exception", e.toString()));
					return false;
				}
				req.info.put("parsedBody", result);
			}
		}
		return true;
	}

}

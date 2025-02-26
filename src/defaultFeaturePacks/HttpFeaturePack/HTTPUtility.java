package defaultFeaturePacks.HttpFeaturePack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import utilities.UtilityTemplate;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public class HTTPUtility extends UtilityTemplate {
	public static String readFromInputStream(InputStream toRead) throws IOException {
		Boolean run = true;
		String result = "";
		while(run) {
			int temp = toRead.read();
			if(temp != -1) {
				result += (char) temp;
			}else {
				run = false;
			}
		}
		return result;
	}
	@Override
	protected void loop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<String, ? extends Class> handleCommand(String name, Map<String, ? extends Class> params) {
		// TODO Auto-generated method stub
		return null;
	}

}

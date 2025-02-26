package utilities.Logging;

import java.util.ArrayList;
import java.util.Map;

public class Logger {
	private static ArrayList<Log> messageQueue;
	public static void log(int level, String title, Map<String, String> meta_data) {
		synchronized(Logger.messageQueue) {
			Logger.messageQueue.add(new Log(level, title, meta_data));
		}
	}
	
	
}

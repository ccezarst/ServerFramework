package utilities.Logging;

import java.util.Map;

public class Log {
	public String title;
	public int level;
	public Map<String, String> meta_data;
	public Log(int level, String title, Map<String, String> meta_data) {
		this.title = title;
		this.meta_data = meta_data;
		this.level = level;
	}
}

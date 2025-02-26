package CustomThreadPool;

import java.util.Map;

public class CustomThreadTask {
	public String taskType;
	public Map<String, ?> taskParameters;
	public boolean handledByManager = false;
	public boolean accepted = false;
	public CustomThreadTask(String taskType, Map<String, ?> params) {
		this.taskType = taskType;
		this.taskParameters = params;
	}
}

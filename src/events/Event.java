package events;

import java.util.Map;

import CustomThreadPool.CustomThreadTask;

public class Event  extends CustomThreadTask{
	
	public enum Types{
		Process_Request
	}
	
	public Event(String taskType, Map<String, ?> params) {
		super(taskType, params);
		// TODO Auto-generated constructor stub
	}

}

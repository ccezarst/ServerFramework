package events;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import CustomThreadPool.CustomThreadPoolManager;
import CustomThreadPool.TaskNotProcessedException;
import core.ServerCore;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public class EventsManager extends CustomThreadPoolManager{
	protected ServerCore core;
	
	public EventsManager(ServerCore core) {
		this.core = core;
	}
	
	public void addEventProccesor(EventProcessor proc) {
		Logger.log(CommonLogLevels.DEBUG.level, "Added event proccesor to manager: " + proc.name, Collections.singletonMap("proccesor_name", proc.name.toString()));
		this.theoreticalPool.add(proc);
	}
	
	public void addEvent(String eventName, Map<String, Object> params) throws TaskNotProcessedException {
		Map<String, Object> params1 = new HashMap<>();
		params1.putAll(params);
		params1.put("server-core", this.core);
		Logger.log(CommonLogLevels.TRACE.level, "Event created", Map.of("event_name", eventName, "event_parameters", params1.toString()));
		this.addTask(eventName, params1);
	}
}

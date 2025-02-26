package events;

import java.util.Map;

import CustomThreadPool.CustomThread;
import CustomThreadPool.CustomThreadTask;
import core.ServerCore;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public abstract class EventProcessor extends CustomThread{
	public final String name;
	
	public EventProcessor(String name) {
		this.name = name;
	}
	
	@Override
	public void parse(CustomThreadTask task) {
		Logger.log(CommonLogLevels.TRACE.level, "Awaiting to procces an event", Map.of("event_type", task.taskType, "event_proccesor_name", this.name, "event_parameters", task.taskParameters.toString()));
		this.handleEvent(task, (ServerCore)task.taskParameters.get("server-core"));
	}
	public abstract void handleEvent(CustomThreadTask task, ServerCore core);
}

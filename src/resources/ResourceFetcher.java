package resources;

import java.util.Map;

import CustomThreadPool.CustomThread;
import CustomThreadPool.CustomThreadTask;
import core.ServerCore;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public abstract class ResourceFetcher extends CustomThread{
	public final ResourceManager manager;
	public final String name;
	public ResourceFetcher(ResourceManager manager, String name) {
		this.manager = manager;
		this.name = name;
	}
	@Override
	protected final void parse(CustomThreadTask task) {
		Logger.log(CommonLogLevels.TRACE.level, "Awaiting to fetch a resource", Map.of("resource_name", task.taskType, "request_parser_name", this.name));
		Resource<?> value = this.parseRequest(task.taskType, (ServerCore)task.taskParameters.get("server-core"));
		manager.setResource(task.taskType, value);
		value.notifyAll();
	}
	public abstract Resource<?> parseRequest(String resourceName, ServerCore core);
}

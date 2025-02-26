package resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import CustomThreadPool.CustomThreadPoolManager;
import CustomThreadPool.CustomThreadTask;
import core.ServerCore;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public class ResourceManager extends CustomThreadPoolManager{
	@SuppressWarnings("rawtypes")
	protected Map<String, Resource> resourceTable;
	protected ServerCore core;
	public ResourceManager(ServerCore core) {
		this.resourceTable = new HashMap<>();
		this.core = core;
	}
	
	public void addResourceFetcher(ResourceFetcher parser) {
		Logger.log(CommonLogLevels.DEBUG.level, "Added resource fetcher to manager: " + parser.name, Collections.singletonMap("parser_name", parser.name.toString()));
		this.pool.add(parser);
	}
	
	public Resource<?> getResource(String name) {
		Logger.log(CommonLogLevels.TRACE.level, "Fetching resource... " + name, Collections.singletonMap("resource_name", name));
		synchronized(this.resourceTable) {
			if(this.resourceTable.containsKey(name) && this.resourceTable.get(name).doesExpire && System.currentTimeMillis() - this.resourceTable.get(name).creationTime > this.resourceTable.get(name).validTime) {
				// checked if resource is still valid;
				Logger.log(CommonLogLevels.TRACE.level, "Fetched resource from memory" + name, Collections.singletonMap("resource_name", name));
				return this.resourceTable.get(name);
			}else {
				try {
					@SuppressWarnings("rawtypes")
					Resource<?> newResource = new Resource();
					this.resourceTable.put(name, newResource);
					Map<String, Object> params = new HashMap<>();
					params.put("server-core", this.core);
					this.addTask(new CustomThreadTask(name, params));
					newResource.wait(); // hopefully no race conditions causing this to be ignored
					// really bad luck but MIGHT happen
					Logger.log(CommonLogLevels.TRACE.level, "Fetched resource from RequestParser" + name, Collections.singletonMap("resource_name", name));
					return newResource;
				}catch(Exception ex) {
					Logger.log(CommonLogLevels.ERROR.level, "Failed to fetch a resource", Map.of("exception_message", ex.getMessage().toString(), "localized_message", ex.getLocalizedMessage().toString(), "stack_trace", ex.getStackTrace().toString()));
				}
			}
		}
		return null;
	}
	
	public void setResource(String name, Resource<?> res) {
		this.resourceTable.put(name, res);
	}
}

/**
 * 
 */
package core;

import java.util.ArrayList;
import java.util.Map;

import CustomThreadPool.TaskNotProcessedException;
import connection_processors.Connection;
import connection_processors.ConnectionProcessor;
import events.Event;
import events.EventProcessor;
import events.EventsManager;
import middleware_chain.Middleware;
import middleware_chain.MiddlewareChain;
import resources.ResourceFetcher;
import resources.ResourceManager;
import utilities.UtilityTemplate;
import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

/**
 * 
 */
public class ServerCore implements Runnable{
	private final EventsManager eventsManager;
	private final ResourceManager resourceManager;
	private final MiddlewareChain middlewareChain;
	private ArrayList<ConnectionProcessor> reqProcs;
	public ServerCore() {
		this.eventsManager = new EventsManager(this);
		this.resourceManager = new ResourceManager(this);
		this.middlewareChain = new MiddlewareChain();
	}
	public void init() {
		for(ConnectionProcessor proc: this.reqProcs) {
			proc.coreConnectionsPort = (Connection req) -> {this.acceptRequest(req);};
			proc.init(this);
			Thread t = new Thread(proc);
			t.start();
		}
		this.eventsManager.start();
		this.resourceManager.start();
	}
	private ArrayList<Connection> requestQueue;
	private void acceptRequest(Connection req) {
		synchronized(this.requestQueue) {
			this.requestQueue.add(req);
		}
	}
	@Override
	public void run() {
		if(!this.requestQueue.isEmpty()) {
			Connection r = this.requestQueue.remove(0);
			if(this.middlewareChain.process(r)) {
				try {
					this.eventsManager.addEvent(Event.Types.Process_Request.name(), Map.of("Request", r));
				} catch (TaskNotProcessedException e) {
					e.printStackTrace();
					Logger.log(CommonLogLevels.INFO.level, "Request failed to be processed", Map.of("RequestIp", r.ip));
					r.sendError("Request cannot be processed");
				}
			}else {
				r.sendError("Security checks failed.");
			}
		}
	}
	
	public void install(FeaturePack f) {
		for(EventProcessor e: f.getEventProcessors()) {
			eventsManager.addEventProccesor(e);
		}
		for(Middleware m: f.getMiddlewares()) {
			middlewareChain.addMiddleware(m);
		}
		for(ConnectionProcessor r: f.getConnectionProcessors()) {
			this.reqProcs.add(r);
		}
		for(ResourceFetcher rF: f.getResourceFetchers()) {
			this.resourceManager.addResourceFetcher(rF);
		}
		for(UtilityTemplate u: f.getUtilities()) {
			u.start();
		}
	}
	
}

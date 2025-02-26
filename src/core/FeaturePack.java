package core;

import java.util.ArrayList;

import events.EventProcessor;
import middleware_chain.Middleware;
import request_proccesors.RequestProcessor;
import resources.ResourceFetcher;

public abstract class FeaturePack {
	public abstract ArrayList<RequestProcessor> getRequestProcessors();
	public abstract ArrayList<Middleware> getMiddlewares();
	public abstract ArrayList<EventProcessor> getEventProcessors();
	public abstract ArrayList<ResourceFetcher> getResourceFetchers();
}

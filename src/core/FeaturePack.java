package core;

import java.util.ArrayList;

import connection_processors.ConnectionProcessor;
import events.EventProcessor;
import middleware_chain.Middleware;
import resources.ResourceFetcher;
import utilities.UtilityTemplate;

public abstract class FeaturePack {
	public abstract ArrayList<ConnectionProcessor> getConnectionProcessors();
	public abstract ArrayList<Middleware> getMiddlewares();
	public abstract ArrayList<EventProcessor> getEventProcessors();
	public abstract ArrayList<ResourceFetcher> getResourceFetchers();
	public abstract ArrayList<UtilityTemplate> getUtilities();
}

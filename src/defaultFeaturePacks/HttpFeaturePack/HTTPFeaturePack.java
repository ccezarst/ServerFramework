package defaultFeaturePacks.HttpFeaturePack;

import java.util.ArrayList;

import connection_processors.ConnectionProcessor;
import core.FeaturePack;
import events.EventProcessor;
import middleware_chain.Middleware;
import resources.ResourceFetcher;
import utilities.UtilityTemplate;
import defaultFeaturePacks.HttpFeaturePack.RequestParsers.*;
import defaultFeaturePacks.HttpFeaturePack.*;
public class HTTPFeaturePack extends FeaturePack{

	@Override
	public ArrayList<ConnectionProcessor> getConnectionProcessors() {
		ArrayList<ConnectionProcessor> pl = new ArrayList();
		pl.add(new HTTPRequestProcessor());
		return pl;
	}

	@Override
	public ArrayList<Middleware> getMiddlewares() {
		ArrayList<Middleware> pl = new ArrayList();
		pl.add(new HTTPPlainTextBodyParser());
		return pl;
	}

	@Override
	public ArrayList<EventProcessor> getEventProcessors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ResourceFetcher> getResourceFetchers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UtilityTemplate> getUtilities() {
		ArrayList<UtilityTemplate> pl = new ArrayList();
		pl.add(new HTTPUtility());
		return pl;
	}

}

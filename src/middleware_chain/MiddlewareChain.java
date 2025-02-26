package middleware_chain;

import java.util.ArrayList;

import connection_processors.Connection;

public class MiddlewareChain {
	protected ArrayList<Middleware> chain;
	public void addMiddleware(Middleware middleware) {
		this.chain.add(middleware);
		if(this.chain.size() > 1) {
			int counter = 1;
			while(counter < this.chain.size()) {
				if(this.chain.get(counter-1).priority > this.chain.get(counter).priority) {
					Middleware temp = this.chain.get(counter-1);
					this.chain.set(counter-1, this.chain.get(counter));
					this.chain.set(counter, temp);
					counter = 1;
				}
			}	
		}
	}
	public Boolean process(Connection r) {
		synchronized(this.chain) {
			for(Middleware mid: this.chain) {
				if(!mid.process(r)) {
					return false;
				}
			}
		}
		return true;
	}
}

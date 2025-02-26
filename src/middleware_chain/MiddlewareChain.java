package middleware_chain;

import java.util.ArrayList;

import request_proccesors.Connection;

public class MiddlewareChain {
	public ArrayList<Middleware> chain;
	
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

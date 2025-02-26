package middleware_chain;

import request_proccesors.Connection;

public interface Middleware {
	public Boolean process(Connection r);
}

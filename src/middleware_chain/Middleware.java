package middleware_chain;

import connection_processors.Connection;

public abstract class Middleware {
	public float priority;
	public enum CommonPriorityLevels{
		Security(9),
		End(10),
		DataValidation(1),
		DataFormating(2),
		AdditionalInformationParsing(3);
		public float value;
		CommonPriorityLevels(float value){
			this.value = value;
		}
	}
	public Middleware(float priority) {
		this.priority = priority;
	}
	public Middleware(CommonPriorityLevels level) {
		this(level.value);
	}
	public abstract Boolean process(Connection r);
}

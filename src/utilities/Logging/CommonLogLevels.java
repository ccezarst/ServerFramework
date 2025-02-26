package utilities.Logging;

public enum CommonLogLevels {
	CRITICAL(0),
	IMPORTANT(1),
	ERROR(2),
	WARN(3),
	INFO(4),
	DEBUG(5),
	TRACE(6),
	ALL(100);
	public int level;
	CommonLogLevels(int level) {
		this.level = level;
	}
}

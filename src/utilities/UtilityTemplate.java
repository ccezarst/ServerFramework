package utilities;

import java.util.ArrayList;
import java.util.Map;

import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public abstract class UtilityTemplate extends Thread{
	public static class UtillityCommand{
		public String name;
		public Map<String, ? extends Class> params;
		public Map<String, ? extends Class> returnData;
		public UtillityCommand(String name, Map<String, ? extends Class> params) {
			this.name = name;
			this.params = params;
		}
	}
	protected static ArrayList<UtillityCommand> commandQueue;
	protected static Map<String, ? extends Class> addCommand(String name, Map<String, ? extends Class> params) {
		UtilityTemplate.UtillityCommand com = new UtillityCommand(name, params);
		synchronized(commandQueue) {
			UtilityTemplate.commandQueue.add(com);
		}
		try {
			com.wait(Configs.requestSetting("framework.utillity.maxWaitTimeForCommand", null));
			return com.returnData;
		} catch (InterruptedException e) {
			Logger.log(CommonLogLevels.ERROR.level, "Waiting for utillity command completion was interrupted", Map.of("commandName", "com.name"));
		}
		return null;
	}
	public void run() {
		while(true) {
			// idk but it might be a good idea to place this inside a synchronized bloc :/
			if(!this.commandQueue.isEmpty()) {
				UtillityCommand current = this.commandQueue.remove(0);
				current.returnData = this.handleCommand(current.name, current.params);
			}
			this.loop();
		}
	}
	protected abstract void loop();
	protected abstract Map<String, ? extends Class> handleCommand(String name, Map<String, ? extends Class> params);
}

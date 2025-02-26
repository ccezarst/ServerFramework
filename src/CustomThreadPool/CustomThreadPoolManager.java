package CustomThreadPool;

import java.util.ArrayList;
import java.util.Map;

import utilities.Logging.CommonLogLevels;
import utilities.Logging.Logger;

public class CustomThreadPoolManager extends Thread{
	protected ArrayList<CustomThread> theoreticalPool;
	protected ArrayList<CustomThread> pool;
	protected ArrayList<CustomThreadTask> taskQueue;
	public boolean keepRunning = true;
	
	
	public CustomThreadPoolManager() {
		this.pool = new ArrayList<>();
	}
	
	public void addThreadToPool(CustomThread thread) {
		synchronized(this.pool) {
			thread.manager = this;
			this.theoreticalPool.add(thread);
		}
	}
	
	public void addTask(String taskName, Map<String, ?> taskParameters) throws TaskNotProcessedException{
		synchronized(this.taskQueue) {
			this.addTask(new CustomThreadTask(taskName, taskParameters));
		}
		
	}
	
	public void addTask(CustomThreadTask task) throws TaskNotProcessedException{
		synchronized(this.taskQueue) {
			this.taskQueue.add(task);
			while(task.handledByManager == false) {};
			if(!task.accepted) {
				throw new TaskNotProcessedException();
			}
		}
	}
	
	
	protected boolean sendTask(CustomThreadTask task, CustomThread thread, boolean shouldParse) {
		synchronized(thread) {
			if(!thread.isBusy) {
				thread.task = task;
				thread.pendingTask = true;
				thread.shouldParse = shouldParse;
				while(thread.pendingTask == true) {}
				return thread.acceptTask;
			}else {
				return false;
			}	
		}
	}
	
	public void removeTaskFromQueue(String name) {
		synchronized(this.taskQueue) {
			for(CustomThreadTask task : this.taskQueue) {
				if(task.taskType == name) {
					this.taskQueue.remove(task);
					return ;
				}
			}
		}
	}
	
	public final void stopManager() {
		this.keepRunning = false;
	}
	@Override
	public void run() {
		while(keepRunning) {
			for(CustomThreadTask task: this.taskQueue) {
				boolean found = false;
				for(CustomThread thread : this.theoreticalPool) {
					if(this.sendTask(task, thread, false)) {
						this.taskQueue.remove(task);
						boolean sent = false;
						for(CustomThread aThread : this.pool) {
							if(this.sendTask(task, aThread, true)) {
								sent = true;
								task.accepted  = true;
								task.handledByManager = true;
								break;
							}
						}
						if(sent == false) { // means is busy/ doesn't yet exist
							try {
								CustomThread newCopy = (CustomThread) thread.clone();
								this.pool.add(newCopy);
								// todo: add expiration logic
								this.sendTask(task, thread, true);
								task.accepted  = true;
								task.handledByManager = true;
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Logger.log(CommonLogLevels.ERROR.level, "Failed to clone CustomThread from theoretical pool into actual pool to execute task" , Map.of("taskType", task.taskType, "taskParameters", task.taskParameters.toString()));
							}
						}
						found = true;
						break;
					}
				}
				if(!found) {
					task.accepted = false;
					task.handledByManager = true;
				}
			}
		}
		for(CustomThread thread : this.pool) {
			thread.keepRunning = false;
		}
	}
}

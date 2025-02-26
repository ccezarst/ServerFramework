package CustomThreadPool;

public abstract class CustomThread implements Runnable, Cloneable {
	
	public boolean isBusy = false;
	public boolean acceptTask = false;
	public boolean pendingTask = false;
	public boolean keepRunning = true;
	public boolean shouldParse = false;
	public CustomThreadTask task;
	public CustomThreadPoolManager manager;
	@Override
	public final void run() {
		while(this.keepRunning) {
			if(this.pendingTask) {
				if(this.accept(this.task)) {
					this.isBusy = true;
					this.acceptTask = true;
					this.pendingTask = false;
					if(this.shouldParse) {
						this.parse(task);
					}
					this.isBusy = false;
					this.acceptTask = false;
					this.shouldParse = false;
					this.notifyAll();
				}else {
					this.isBusy = false;
					this.acceptTask = false;
					this.pendingTask = false;
					this.notifyAll();
				}
			}
		}
		this.exit();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	protected abstract boolean accept(CustomThreadTask task);
	protected abstract void parse(CustomThreadTask task);
	protected abstract void exit();
}

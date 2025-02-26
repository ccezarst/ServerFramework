package resources;

public class Resource<T> {
	public T value;
	public long creationTime = 0;
	public long validTime = 0;
	public boolean doesExpire = true;
	
	public Resource() {}
	
	public void setFields(T value, boolean doesExpire, long validTime) {
		this.value = value;
		this.creationTime = System.currentTimeMillis();
		this.validTime = validTime;
		this.doesExpire = doesExpire;
	}
	
	public void setValue(T newValue) {
		this.value = newValue;
		this.notifyAll();
	}
}

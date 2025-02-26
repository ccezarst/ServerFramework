package CustomThreadPool;

public class TaskNotProcessedException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotProcessedException () {

    }

    public TaskNotProcessedException (String message) {
        super (message);
    }

    public TaskNotProcessedException (Throwable cause) {
        super (cause);
    }

    public TaskNotProcessedException (String message, Throwable cause) {
        super (message, cause);
    }
}

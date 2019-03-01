package concurrency.resourcepool;

public class ResourceNotAvailableException extends Exception {

	private static final long serialVersionUID = 2347244200017220734L;

	public ResourceNotAvailableException(String message) {
		super(message);
	}

}

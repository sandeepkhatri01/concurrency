package concurrency.resourcepool;

public interface Poolable<T> {

	void releaseResource(T t);

	T getResource() throws ResourceCreationException, ResourceNotAvailableException;
}

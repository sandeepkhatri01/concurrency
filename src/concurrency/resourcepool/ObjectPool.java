package concurrency.resourcepool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ObjectPool<T> implements Poolable<T> {

	@SuppressWarnings("unused")
	private int maxResources;
	private int waitTime;
	private Queue<T> pool;
	private Semaphore semaphore;
	private Supplier<T> supplier;

	public ObjectPool(int maxResources, int waitTime, Supplier<T> supplier) {
		this.maxResources = maxResources;
		this.waitTime = waitTime;
		pool = new ConcurrentLinkedQueue<>();
		semaphore = new Semaphore(maxResources);
		this.supplier = supplier;

	}

	@Override
	public void releaseResource(T t) {
		pool.add(t);
		semaphore.release();
	}

	@Override
	public T getResource() throws ResourceCreationException, ResourceNotAvailableException {

		try {
			if (semaphore.tryAcquire(waitTime, TimeUnit.SECONDS)) {

				T resource = pool.poll();
				if (resource != null) {
					return resource;
				}
				try {
					return createResource();

				} catch (Exception ex) {
					semaphore.release();
					throw new ResourceCreationException("Error in creating resource");

				}
			}
			throw new ResourceNotAvailableException("Resource not available");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		throw new ResourceNotAvailableException("Resource not available");
	}

	private T createResource() {
		return supplier.get();
	}

}

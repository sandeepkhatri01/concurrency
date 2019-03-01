package concurrency.resourcepool;

public class Main {

	public static void main(String[] args) throws ResourceCreationException, ResourceNotAvailableException {
		final Poolable<String> pool = new ObjectPool<String>(2, 3, () -> {
			return new String("Connection");
		});

		String string1 = pool.getResource();
		String string2 = pool.getResource();

	}

}

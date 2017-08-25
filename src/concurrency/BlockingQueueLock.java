package concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("javadoc")
public class BlockingQueueLock<T> {

    private int capacity;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();

    private Condition notFull = lock.newCondition();

    private Queue<T> queue = new LinkedList<>();

    public BlockingQueueLock(int capacity) {
        this.capacity = capacity;
    }

    public T dequeue() throws InterruptedException {
        try {
            lock.lock();

            while (queue.isEmpty()) {
                notEmpty.await();
            }

            T item = queue.remove();
            notFull.signal();
            return item;

        } finally {
            lock.unlock();
        }

    }

    public void enqueue(T t) throws InterruptedException {

        try {
            lock.lock();

            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(t);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

package concurrency;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("javadoc")
public class BlockingQueueWaitNotify<T> {

    @SuppressWarnings("boxing")
    public static void main(String[] args) throws InterruptedException {
        BlockingQueueWaitNotify<Integer> bqueue = new BlockingQueueWaitNotify(2);
        bqueue.enqueue(1);
        bqueue.enqueue(2);
        bqueue.enqueue(3);

    }

    private int capacity;

    private Queue<T> queue = new LinkedList<>();

    public BlockingQueueWaitNotify(int capacity) {
        this.capacity = capacity;
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.remove();
        notifyAll();
        return item;
    }

    public synchronized void enqueue(T t) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(t);
        notifyAll();
    }

}

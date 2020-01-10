import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

class BoundedBuffer {
    int[] array;
    int size = 0;
    int capacity = 0;
    int head = 0;
    int tail = 0;
    Lock lock  = new ReentrantLock();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        array = new int[this.capacity];
    }

    public void enq(int item) throws InterruptedException {
        lock.lock();
        while(size == capacity) {
            lock.unlock();
            lock.lock();
        }
        if(tail == capacity) {
            tail = 0;
        }
        array[tail++] = item;
        size++;
        lock.unlock(); 
    }

    public int deq() throws InterruptedException {
        lock.lock();
        while(size == 0) {
            lock.unlock();
            lock.lock();
        }
        if(head == capacity) {
            head = 0;
        }
        int item = array[head];
        head++;
        size--;
        lock.unlock();
        return item;
    }
}

public class BoundedBufferMutex {
    public static void main(String args[]) throws InterruptedException {
        BoundedBuffer q = new BoundedBuffer(5);
       Thread producer1 = new Thread(new Runnable() {
            public void run() {
                try {
                    int i = 1;
                    while (true) {
                        q.enq(i);
                        System.out.println("Producer thread 1 enqueued " + i);
                        i++;
                    }
                } catch (InterruptedException ie) {
                }
            }
        }); 
        Thread producer2 = new Thread(new Runnable() {
            public void run() {
                try {
                    int i = 5000;
                    while (true) {
                        q.enq(i);
                        System.out.println("Producer thread 2 enqueued " + i);
                        i++;
                    }
                } catch (InterruptedException ie) {

                }
            }
        });        
        Thread producer3 = new Thread(new Runnable() {
            public void run() {
                try {
                    int i = 100000;
                    while (true) {
                        q.enq(i);
                        System.out.println("Producer thread 3 enqueued " + i);
                        i++;
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread consumer1 = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("Consumer thread 1 dequeued " + q.deq());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread consumer2 = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("Consumer thread 2 dequeued " + q.deq());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread consumer3 = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        System.out.println("Consumer thread 3 dequeued " + q.deq());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });
        producer1.setDaemon(true);
        producer2.setDaemon(true);
        producer3.setDaemon(true);
        consumer1.setDaemon(true);
        consumer2.setDaemon(true);
        consumer3.setDaemon(true);

        producer1.start();
        producer2.start();
        producer3.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();

        Thread.sleep(1000); 
    }
}
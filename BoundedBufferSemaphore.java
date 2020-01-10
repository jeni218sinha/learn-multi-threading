

class BoundedBufferSem {
     int[] array;
    int size = 0;
    int capacity = 0;
    int head = 0;
    int tail = 0;
    CountingSemaphore semLock;
    CountingSemaphore semItems;
    public BoundedBufferSem(int capacity) {
        array = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
        semLock = new CountingSemaphore(1,1);
        semItems = new CountingSemaphore(capacity, 0);
    }

    public void enq(int item) throws InterruptedException{
        semItems.release();
        semLock.acquire();
        if(tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        tail++;
        size++;
        semLock.release();
    }

    public synchronized int deq() throws InterruptedException {
        semItems.acquire();
        semLock.acquire();
        if(head == capacity) {
            head = 0;
        }
        int item = array[head];
        head++;
        size--;
        semLock.release();
        return item;
    }   
}


public class BoundedBufferSemaphore {
   public static void main( String args[] ) throws InterruptedException {
        final BoundedBufferSem q = new BoundedBufferSem(5);

        Thread t1 = new Thread(new Runnable() {

            public void run() {
                try {
                    for (int i = 0; i < 20; i++) {
                        q.enq(new Integer(i));
                        System.out.println("enqueued " + i);
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Thread 2 dequeued: " + q.deq());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        Thread t3 = new Thread(new Runnable() {

            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("Thread 3 dequeued: " + q.deq());
                    }
                } catch (InterruptedException ie) {

                }
            }
        });

        t1.start();
        Thread.sleep(4000);
        t2.start();
        t2.join();

        t3.start();
        t1.join();
        t3.join();
    }
}

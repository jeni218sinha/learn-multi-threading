

class BlockingQ {
    int[] array;
    int size = 0;
    int capacity = 0;
    int head = 0;
    int tail = 0;
    public BlockingQ(int capacity) {
        array = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public synchronized void enq(int item) {

        while(size == capacity) {
            try {
                wait();
            } catch(Exception e) {
                System.out.println("thread got interrupted");
            }
        }
        if(tail == capacity) {
            tail = 0;
        }
        array[tail] = item;
        tail++;
        size++;
        notifyAll();
    }

    public synchronized int deq() {
        while(size == 0) {
            try {
                wait();
            } catch(Exception e) {
                System.out.println("thread got interrupted");
            }
        }
        if(head == capacity) {
            head = 0;
        }
        int item = array[head];
        head++;
        size--;
        notifyAll();
        return item;
    }
}

public class BoundedBuffer {
    public static void main(String args[]) throws InterruptedException {
        final BlockingQ q = new BlockingQ(5);
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    for(int i = 0; i < 50; i++) {
                        q.enq(i);
                        System.out.println(" Enqueued "+ i);
                    }
                } catch(Exception e) {

                }
            }
        });
        Thread t2 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for(int i = 0; i < 25; i++) {
                    System.out.println(" Dequeud" + q.deq());

                }    
            }
        });
        Thread t3 = new Thread(new Runnable(){
        
            @Override
            public void run() {
                for(int i = 0; i < 25; i++) {
                    System.out.println(" Dequeud" + q.deq());

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
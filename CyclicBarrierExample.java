
import java.util.concurrent.CyclicBarrier;

class Task implements Runnable {
    private CyclicBarrier barrier;
    public Task(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    public void run() {
        try {
            System.out.println(" Thread " + Thread.currentThread().getName() + " is waiting on barrier");
            barrier.await();
            System.out.println(" Thread "+ Thread.currentThread().getName() + " has crossed the barrier");
        } catch (Exception e) {
            System.out.println(" Thread " + Thread.currentThread().getName() + " failed ");
        }
    }
}

public class CyclicBarrierExample {
    public static void main(String aths[]) {
        final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            public void run() {
                System.out.println("All have reached the barrier, lets go aghead now");     
            }
        });
        Thread t1 = new Thread(new Task(barrier), " Thread 1");
        Thread t2 = new Thread(new Task(barrier), " Thread 2");
        Thread t3 = new Thread(new Task(barrier), " Thread 3");          
        
        t1.start();
        t2.start();
        t3.start();
    }
}
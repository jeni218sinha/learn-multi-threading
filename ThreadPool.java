import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final int numberOfThreads;
    private final PoolThread[] threads;
    private LinkedBlockingQueue queue;

    public ThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        threads = new PoolThread[this.numberOfThreads];
        queue = new LinkedBlockingQueue<>();
        for(int i = 0; i < this.numberOfThreads; i++) {
            threads[i] = new PoolThread(i, queue);
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized(queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public static void main(String args[]) {
        ThreadPool threadPool = new ThreadPool(7);
        for(int i = 0; i < 10; i++) {
            Runnable task = new PoolTask(""+ i);
            threadPool.execute(task);
        }
    }
}

 class PoolThread extends Thread {
    private LinkedBlockingQueue queue;
    private final int num;
    PoolThread(int i, LinkedBlockingQueue q) {
        num = i;
        queue = q;
    } 
    public void run() {
        Runnable task;
        while(true) {
            synchronized(queue) {
                while(queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch(Exception e) {
                        System.out.println(" Thread " + Thread.currentThread().getName() + " num "+ num + " got interrupted");
                    }
                }
                task = (Runnable) queue.poll();
            }
            try {
                task.run();
            } catch (Exception e) {
                System.out.println(" Error while executing the task");
            }
        }
     }
 }

 class PoolTask implements Runnable {
     String name;

     public PoolTask(String name) {
         this.name = name;
     }

     public void run() {
         System.out.println(" Running Pool Task  "+ name);
     }
 }
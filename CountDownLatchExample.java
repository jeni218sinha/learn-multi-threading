import java.util.concurrent.CountDownLatch;

class Worker extends Thread {
    private CountDownLatch countDownLatch;
    public Worker(CountDownLatch countDownLatch, String name) {
        super(name);
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println(" Worker " + Thread.currentThread().getName() + " started");
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Worker " + Thread.currentThread().getName() + " completed");
        countDownLatch.countDown();
    }
}

class Master extends Thread {
    private CountDownLatch countDownLatch;
    public Master(CountDownLatch countDownLatch, String name) {
        super(name);
        this.countDownLatch = countDownLatch;
    }
    public void run() {
        try {
            countDownLatch.await();
        System.out.println(" Master thread "+ Thread.currentThread().getName() + " started ");
            Thread.sleep(2000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class CountDownLatchExample {
    public static void main(String args[]) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Worker A = new Worker(countDownLatch, "A");
        Worker B = new Worker(countDownLatch, "B");
        A.start();
        B.start();
        Master master = new Master(countDownLatch,"Master of A&B");
        master.start();
    }
}
import java.util.*;

public class MultiThreadTokenBucket {
    private final int maxTokens;
    private long possibleTokens;

    public MultiThreadTokenBucket(int maxTokens) {
        this.maxTokens = maxTokens;
        possibleTokens = 0;
        Thread t = new Thread(()->{
            generateTokens();
        });
        t.setDaemon(true);
        t.start();
    }

    private void generateTokens() {
        while(true) {
            synchronized(this) {
                if(possibleTokens < maxTokens) {
                    possibleTokens++;
                }
                this.notify();
            }
            try {
                Thread.sleep(1000);
            } catch(Exception e) {

            }
        }
    }

    public void getToken() throws InterruptedException {
        synchronized(this) {
            while(possibleTokens == 0) {
                this.wait();
            }
            possibleTokens--;
        }
        System.out.println(" granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis()/1000);
    }

    public static void main(String args[]) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final MultiThreadTokenBucket tokenBucketFilter = new MultiThreadTokenBucket(1);

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {

                public void run() {
                    try {
                        tokenBucketFilter.getToken();
                    } catch (InterruptedException ie) {
                        System.out.println("We have a problem");
                    }
                }
            });
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}
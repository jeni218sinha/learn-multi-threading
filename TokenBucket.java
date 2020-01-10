
import java.util.*;
public class TokenBucket {
    private final int maxTokens;
    private long lastRequest;
    long possiblezzTokens = 0;
    
    public TokenBucket(int maxTokens) {
        this.maxTokens = maxTokens;
        lastRequest = System.currentTimeMillis();
    }

    public synchronized void getToken() throws InterruptedException {
        //  get tokens genrated since lastToken
        possiblezzTokens += (System.currentTimeMillis() - lastRequest) / 1000;
        if(possiblezzTokens > maxTokens) {
            possiblezzTokens = maxTokens;
        }
        if(possiblezzTokens == 0) {
            Thread.sleep(1000);
        } else {
            possiblezzTokens--;
        }
        lastRequest = System.currentTimeMillis();
        System.out.println(" Granting access to " + Thread.currentThread().getName() +" token at " + System.currentTimeMillis());
    }

    public static void main(String[] args) throws InterruptedException {
       Set<Thread> allThreads = new HashSet<Thread>();
        final TokenBucket tokenBucketFilter = new TokenBucket(1);

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
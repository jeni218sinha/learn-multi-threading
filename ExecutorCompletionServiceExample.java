import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorCompletionServiceExample {
    static Random random = new Random(System.currentTimeMillis());
    public static void main(String arhs[]) throws Exception {
        completionServiceExample();
    }
    public static void completionServiceExample() throws Exception {
        class TrivialTask implements Runnable {
            int n;
            public TrivialTask(int n) {
                this.n = n;
            }
            public void run() {
                try {
                    // sleep for one second
                    Thread.sleep(random.nextInt(101));
                    System.out.println(n*n);
                } catch (InterruptedException ie) {
                    // swallow exception
                }
            }
            
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<Integer>(threadPool);
        for(int i = 1; i <= 10; i++) {
            service.submit(new TrivialTask(i), new Integer(i));
        }
        int count = 10;
        while(count != 0) {
            Future<Integer> future = service.poll();
            if(future != null) {
                System.out.println("Thread " + future.get() + " got done");
                count--;
            }
        }
        threadPool.shutdown();
    }
}
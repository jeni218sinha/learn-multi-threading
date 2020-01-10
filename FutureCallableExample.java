import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class FutureCallableExample {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    public static void main(String args[]) throws Exception {
        // System.out.println(findSum(10));
        findSumRun(10);
        threadPool.shutdown();
    }

    static int findSum(final int n) throws Exception {
        Callable<Integer> sumTask = new  Callable<Integer>() {
            public Integer call() throws Exception {
                int sum = 0;
                for(int i = 1; i <= n; i++) {
                    sum += i;
                }
                return sum;
            }
        };
        Future<Integer> future = threadPool.submit(sumTask);
        while(!future.isDone()) {
            System.out.println("Waiting for sum complete");
        }
        return future.get();
    }

    static void findSumRun(int n) throws Exception {
        MyTask myTask = new MyTask(n);
         Future<?> future =  threadPool.submit(myTask);
         while(!future.isDone()) {
             System.out.println(" Not completed");
         }
         future.get();
        return;
    }

    static class MyTask implements Runnable {
        int n;
        MyTask(int n) {
            this.n = n;
        }
        public void run() {
            int sum = 0;
            for(int i = 1;i <= n; i++) {
                sum += i;
            }
            System.out.println(" Sum is sum " + sum);
        }        
    }
}
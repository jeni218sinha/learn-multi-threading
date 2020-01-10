import java.util.concurrent.*;
public class ThreadExecutor {
    public static void main(String args[]) {
        MyTask myTask = new MyTask();
        DumdExecutor dumdExecutor = new DumdExecutor();
        dumdExecutor.execute(myTask);
    }

    static class MyTask implements Runnable {
        public void run() {
            System.out.println("Running my task");
        }
    }

    static class DumdExecutor implements Executor {
        public void execute(Runnable runnable) {
            Thread t = new Thread(runnable);
            System.out.println(" Starting thread via executor "+ t.getName());
            t.start();
        }
    }
}
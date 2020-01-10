public class ThreadCreationDemo {
    public static void main(String args[]) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello you are inside a thread");
            }
        });
        ExecuteMe executeMe = new ExecuteMe();
        Thread t2 = new Thread(executeMe);
        ExecuteMeThread executeMeThread = new ExecuteMeThread();
        System.out.println(java.time.Clock.systemUTC().instant());
        t1.start();
        t2.start();
        t1.sleep(5000);
        t1.join();
        System.out.println(java.time.Clock.systemUTC().instant());
        executeMeThread.start();
        executeMeThread.join();
        System.out.println(" Will wait for t1 to complete not for t2");
        t2.join();
        System.out.println(" Will wait only for t2");
        System.out.println(java.time.Clock.systemUTC().instant());
    }
}

class ExecuteMe implements Runnable {
 
  public void run() {
    System.out.println("Say Hello to second thread");
  }

}

class ExecuteMeThread extends Thread {
  
  @Override
  public void run() {
    System.out.println("I ran after extending Thread class");
  }
  
}
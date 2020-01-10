import java.util.concurrent.Callable;

class SumExample implements Callable<Integer> {
    int n;
    SumExample(int n) {
        this.n = n;
    }

    public Integer call() throws Exception{
        int sum = 0;
        for(int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

}

public class CallableExample {
    public static void main(String args[])throws Exception {
        SumExample sm = new SumExample(10);
        System.out.println("Sum of "+sm.n +" = "+ sm.call());
        final int n = 10;
        Callable<Integer> exampCallable = new Callable<Integer>() {
            public Integer call() throws Exception {
            int sum = 0;
            for(int i = 1;  i <= n; i++) {
                sum += i;
            }
            return  sum;
        }
        };
        System.out.println(exampCallable.call());
    }
}
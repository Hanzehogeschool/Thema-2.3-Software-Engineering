import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sum {

    private Integer sum;

    public Sum() {
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        sum = 0;

        for (int i = 0; i < 1000; i++) {
            executor.execute(new SumTask());
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        Sum sum = new Sum();
        System.out.println("What is sum? " + sum.sum);
    }

    class SumTask implements Runnable {

        public void run() {
            sum += 1;
            System.out.println(sum);
        }

    }

}

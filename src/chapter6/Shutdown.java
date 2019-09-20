package chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Shutdown {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.submit(() -> {
            for (int i = 0; ; i++) {
                threadPool.submit(() -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(".");
                });
            }
        });
        TimeUnit.SECONDS.sleep(1);

        System.out.println();
        System.out.println("initiating shutdown");

        threadPool.shutdown();
    }
}

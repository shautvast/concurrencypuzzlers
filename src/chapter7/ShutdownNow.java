package chapter7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShutdownNow {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.submit(() -> {
            for (int i = 0; ; i++) {
                threadPool.submit(() -> {
                    try {
                        TimeUnit.MICROSECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print(".");
                });
            }
        });
        TimeUnit.SECONDS.sleep(1);
        System.out.printf("%ninitiating shutdown Now%n");

        threadPool.shutdownNow();
    }
}

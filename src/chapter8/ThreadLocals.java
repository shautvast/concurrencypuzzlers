package chapter8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocals {
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        threadPool.submit(() -> {
            HOLDER.set("value");
            throw new RuntimeException("foo");
        });

        threadPool.submit(() -> {
            String value = HOLDER.get();
            if (!"value".equals(value)) {
                System.out.println("Value is not set");
            }
        });
    }
}

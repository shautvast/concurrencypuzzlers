package chapter6.futures;

import java.util.concurrent.*;

public class CompletionServiceExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 1; i < 11; i++) {
            final int increment = i;
            completionService.submit(() -> {
                TimeUnit.SECONDS.sleep(increment);
                return increment;
            });
        }

        for (int i = 0; i < 10; i++) {
            Future<Integer> future = completionService.take();
            int value = future.get();
            System.out.println(value);
        }
    }
}

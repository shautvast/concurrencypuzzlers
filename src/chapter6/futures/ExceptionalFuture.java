package chapter6.futures;

import java.util.concurrent.*;

public class ExceptionalFuture {

    private ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new ExceptionalFuture().run();
    }

    @SuppressWarnings("all")
    private void run() throws InterruptedException, ExecutionException {
        Future<String> futureData = getData();
        System.out.printf("done: %b%n", futureData.isDone());
        System.out.printf("cancelled: %b%n", futureData.isCancelled());

        System.out.printf("result: %s%n", futureData.get());

        executor.shutdownNow();
    }

    private Future<String> getData() {
        return executor.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            throw new RuntimeException("exception is rule");
        });
    }
}

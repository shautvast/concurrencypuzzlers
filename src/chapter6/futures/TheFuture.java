package chapter6.futures;

import java.util.concurrent.*;

public class TheFuture {

    private ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new TheFuture().run();
    }

    private void run() throws InterruptedException, ExecutionException {
        Future<String> futureData = getData();
        System.out.printf("done: %b%n", futureData.isDone());
        System.out.printf("cancelled: %b%n", futureData.isCancelled());

        futureData.cancel(true);

        System.out.printf("result: %s%n", futureData.get());
        System.out.printf("done: %b%n", futureData.isDone());
        System.out.printf("cancelled: %b%n", futureData.isCancelled());
    }

    private Future<String> getData() {
        return executor.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            return "data";
        });
    }
}


package chapter8;

import java.util.concurrent.*;

public class BlockingQueues {
    public static void main(String[] args) {
        BlockingQueue<String> synchronousQueue = new ArrayBlockingQueue<>(10);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        executor.submit(() -> {
            System.out.println("waiting for something...");
            try {
                System.out.println(synchronousQueue.take());
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executor.schedule(() -> {
            synchronousQueue.add("here it is");
            synchronousQueue.add("here it is again");
        }, 2, TimeUnit.SECONDS);

        executor.shutdown();
    }
}

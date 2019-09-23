package chapter8;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueues {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        synchronousQueue.add("here is your value");

        Thread thread = new Thread(() -> {
            System.out.println("waiting for something...");
            try {
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(true);
        thread.start();

        executor.schedule(() -> {
            synchronousQueue.add("here it is");
        }, 2, TimeUnit.SECONDS);

        executor.shutdown();
    }
}

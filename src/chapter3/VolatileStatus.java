package chapter3;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VolatileStatus {
    //shared mutable variable
    private static boolean finished = false;

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);

        // simulate some task in other thread
        int waitTime = new Random().nextInt(10);
        System.out.printf("task takes %d seconds \n", waitTime);
        threadPool.schedule(() -> {
            finished = true;
        }, waitTime, TimeUnit.SECONDS);

        // main thread waits for finished to be true
        threadPool.submit(() -> {
            int count = 0;
            long t0 = System.currentTimeMillis();

            while (!finished) {
                System.out.printf("waiting for task to finish, %d\n", ++count);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // ignore for now
                }
            }

            long t1=System.currentTimeMillis();
            long actualTime=t1-t0;

            System.out.printf("waited %d milliseconds", actualTime);
        });

        //orderly shutdown
        threadPool.shutdown();
    }
}

package chapter1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Waiter {


    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        //wait after 2 seconds
        executor.schedule(() -> {
            synchronized (lock){
                try {
                    System.out.println("2 wait again");
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 2, TimeUnit.SECONDS);

        //release lock after 4 seconds
        executor.schedule(() -> {
            synchronized (lock){
                System.out.println("3 notifying");
                lock.notify();
            }
        }, 4, TimeUnit.SECONDS);

        // wait now
        System.out.println("1 wait");
        synchronized (lock) {
            lock.wait();
        }

        System.out.println("4 wait no more");

        executor.shutdownNow();
    }
}

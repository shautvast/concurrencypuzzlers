package chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TrickyTaskCancellation {

    private static int count;
    private static int countDown = 5;

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        //show countdown every second
        scheduler.scheduleAtFixedRate(() -> System.out.println(countDown--), 0, 1, SECONDS);

        //schedule interrupt in 5 seconds
        scheduler.schedule(mainThread::interrupt, 5, SECONDS);

        //do long running activity on main-thread while checking the interrupted status
        for (; !mainThread.isInterrupted(); ) {
            count++;
        }

//      What happens if you use this code instead of the above?
//        for (; !Thread.interrupted(); ) {
//            count++;
//        }

        System.out.println("Done.");
        System.out.printf("Thread status = %s\n", mainThread.isInterrupted() ? "interrupted" : "not interrupted");

        scheduler.shutdownNow();
    }
}

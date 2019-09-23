package chapter8;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CustomThreadPoolExecutor {
    private static final List<Long> executionTimes = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start handing tasks");
        ThreadPoolExecutor executor = newTimingExecutor();
        Random random = new Random();

        IntStream.rangeClosed(0, 1).forEach(__ ->
                executor.submit(() -> {
                    try {
                        int time = random.nextInt(1000);
                        System.out.printf("Task working for %d milliseconds%n", time);
                        TimeUnit.MILLISECONDS.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));

        TimeUnit.SECONDS.sleep(2);

        System.out.printf("There were %d tasks with the following durations (in milliseconds): %s", executionTimes.size(), executionTimes);

        executor.shutdown();
    }

    private static ThreadPoolExecutor newTimingExecutor() {
        return new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>()) {
            private final ThreadLocal<Long> starttime = new ThreadLocal<>();

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
                starttime.set(System.currentTimeMillis());
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                long endtime = System.currentTimeMillis();
                long executiontime = endtime - starttime.get();
                executionTimes.add(executiontime);
            }
        };
    }
}

package common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Test Harness for multithreaded code
 */
public class TestHarness {

    private final int threadCount;
    private final CountDownLatch countDownLatch;

    /**
     * Creates a TestHarness
     * @param threadCount the number of threads
     */
    public TestHarness(int threadCount) {
        this.threadCount = threadCount;
        this.countDownLatch = new CountDownLatch(1);
    }

    /*
     * In the tested code, this method must call this method,
     * to make the thread waits for the signal (the moment countDown is called on the latch).
     * This technique (see 5.1.1 in JCiP) makes sure all threads start at exactly the same time,
     * instead of creation time of the runnable. This increases concurrency to the max. */
    public void waitForSignal() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes a runnable using a pool of threads.
     *
     * @param action The Runnable with the code that is te be tested.
     */
    public void testMultiThreaded(Runnable action) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);

        long t0 = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            threadPool.submit(() -> {
                action.run();
            });
        }

        /* start !*/
        countDownLatch.countDown();

        try {
            threadPool.shutdown();
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignored
        }

        long t1 = System.currentTimeMillis();
        System.out.printf("Total execution time in TestHarness: %d milliseconds\n", t1 - t0);
    }
}

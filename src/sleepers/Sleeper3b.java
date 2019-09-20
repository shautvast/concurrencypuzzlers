package sleepers;

import common.TestHarness;
import org.junit.Test;

import java.util.Date;

public class Sleeper3b {

    /* what does this print? */
    public void sleep() {
        synchronized (this) {
            try {
                System.out.printf("[%s] thread %s sleep\n", new Date().toString(), Thread.currentThread().getName());

                this.wait(1000);

                System.out.printf("[%s] thread %s wake up\n",  new Date().toString(), Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @Test
    public void test() {
        TestHarness testHarness = new TestHarness(2);
        Sleeper3b sleeper = new Sleeper3b();

        testHarness.testMultiThreaded(() -> {
            testHarness.waitForSignal();
            sleeper.sleep();
        });

    }
}

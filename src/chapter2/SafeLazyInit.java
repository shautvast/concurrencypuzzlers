package chapter2;

import common.TestHarness;
import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

public class SafeLazyInit {
    private ExpensiveObject expensiveObject = null;

    public synchronized ExpensiveObject getInstance() {
        if (expensiveObject == null) {
            expensiveObject = new ExpensiveObject();
        }
        return expensiveObject;
    }

    @Test
    public void test() {
        TestHarness testHarness = new TestHarness(100);
        LongAdder waitTime=new LongAdder();
        SafeLazyInit safeLazyInit = new SafeLazyInit();

        testHarness.testMultiThreaded(() -> {
            testHarness.waitForSignal();
            long t0=System.nanoTime();
            safeLazyInit.getInstance();
            waitTime.add(System.nanoTime()-t0);
        });

        System.out.printf("Waiting for instance for %f milliseconds per thread\n", (waitTime.doubleValue()/1000000)/100);
    }
}

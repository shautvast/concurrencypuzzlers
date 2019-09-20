package chapter2;

import common.TestHarness;
import org.junit.Test;

public class LazyInit {
    private ExpensiveObject expensiveObject = null;

    public ExpensiveObject getInstance() {
        if (expensiveObject == null) {
            expensiveObject = new ExpensiveObject();
        }
        return expensiveObject;
    }

    @Test
    public void test() {
        TestHarness testHarness = new TestHarness(8);
        LazyInit lazyInit = new LazyInit();

        testHarness.testMultiThreaded(() -> {
            testHarness.waitForSignal();
            lazyInit.getInstance();
        });
    }
}

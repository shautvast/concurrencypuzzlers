package chapter1;

import common.NotThreadSafe;
import common.TestHarness;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Basic example taken from JCIP listing 1.1 (altered slightly)
 */
@NotThreadSafe
public class UnsafeSequence {
    //shared mutable state
    private int value=0;

    private final List<Integer> collectedValues = new CopyOnWriteArrayList<>();

    public void addNextValue() {
        collectedValues.add(++value);
    }

    @Test
    public void test() throws InterruptedException {
        TestHarness testHarness = new TestHarness(8);

        testHarness.testMultiThreaded(() -> {
                    testHarness.waitForSignal();
                    addNextValue();
                }
        );

        System.out.printf("result %s%n",collectedValues);
    }

}

package chapter1;

import common.TestHarness;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Basic example taken from JCIP listing 1.2 (slightly altered)
 */
public class SafeSequence {
    private final List<Integer> collectedValues = new CopyOnWriteArrayList<>();

    private int value = 0;

    //access to shared mutable state is now made sequential
    public synchronized void addNextValue() {
        collectedValues.add(++value);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void test() throws InterruptedException {
        TestHarness testHarness = new TestHarness(8);

        testHarness.testMultiThreaded(() -> {
                    testHarness.waitForSignal();
                    addNextValue();
                }
        );

        System.out.printf("result %s%n", collectedValues);
    }

}

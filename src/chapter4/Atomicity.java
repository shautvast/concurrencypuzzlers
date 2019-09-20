package chapter4;

import common.TestHarness;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class Atomicity {
    private final CopyOnWriteArrayList<String> values = new CopyOnWriteArrayList<>();

    @Test
    public void test() {
        new TestHarness(8).testMultiThreaded(() -> this.addOneEntry());
        Assert.assertEquals(1, values.size());
    }

    public void addOneEntry() {
        if (!values.contains("value")) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            values.add("value");
        }
    }
}

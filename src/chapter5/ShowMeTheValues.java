package chapter5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* Will this give me the values? */
public class ShowMeTheValues {

    private final List<Integer> values = new ArrayList<>();
    private volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {
        new ShowMeTheValues().run();
    }

    private void run() throws InterruptedException {
        new Thread(() -> {
            for (; ; ) {
                values.add(i++);
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println(values);
    }
}

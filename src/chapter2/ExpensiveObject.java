package chapter2;

import java.util.concurrent.TimeUnit;

public class ExpensiveObject {

    public ExpensiveObject() {
        /* fake long execution time*/
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // ignored
        }
        System.out.printf("Thread %s: new instance created (%s) \n", Thread.currentThread().getName(), this);
    }
}

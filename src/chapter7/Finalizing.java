package chapter7;

import java.util.concurrent.TimeUnit;

public class Finalizing {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Finalizing();
        }
        System.gc();
        TimeUnit.SECONDS.sleep(10);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalizing...");

        TimeUnit.SECONDS.sleep(1);

        System.out.printf("is taking a long time for %s%n", Thread.currentThread().getName());

        throw new Exception("oops");
    }
}

package sleepers;

/*
 * javadoc for Thread.yield:
 *
 * It is rarely appropriate to use this method. It may be useful
 * for debugging or testing purposes, where it may help to reproduce
 * bugs due to race conditions.
 */
public class Sleeper4 {
    public static void main(String[] args) {
        System.out.println("sleep with yield");

        int count = 0;
        long t0 = System.currentTimeMillis();
        while (System.currentTimeMillis() - t0 < 1000) {
            Thread.yield();
            count++;
        }
        System.out.println("wake up");
        System.out.printf("System.currentTimeMillis was called %d times%n", count);

        System.out.printf("%nsleep without yield%n");
        count = 0;
        t0 = System.currentTimeMillis();
        while (System.currentTimeMillis() - t0 < 1000) {
            count++;
        }

        System.out.println("wake up");
        System.out.printf("System.currentTimeMillis was called %d times%n", count);
    }
}

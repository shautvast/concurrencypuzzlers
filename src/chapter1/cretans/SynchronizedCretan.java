package chapter1.cretans;

import org.junit.Test;

/*
 * update is now synchronized. Will it still WTF ?
 */
public class SynchronizedCretan extends CretanAttack{

    public double value;

    public SynchronizedCretan(double value) {
        super(value);
    }

    @Test
    public void test() {
        SynchronizedCretan instance = new SynchronizedCretan(0);
        instance.test();
    }

    public synchronized void update() {
        value++;
    }

    public void compare() {
        if (value != value) {
            System.out.println("WTF?");
        }
    }
}

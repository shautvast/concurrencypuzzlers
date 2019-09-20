package chapter1.cretans;

/*
 * now both read and update are synchronized
 */
public class SynchronizedCretan2 extends CretanAttack {

    public double value;

    public SynchronizedCretan2(double value) {
        super(value);
    }

    public static void main(String[] args) {
        SynchronizedCretan2 instance = new SynchronizedCretan2(0);
        instance.test();
    }

    public synchronized void update() {
        value++;
    }

    public synchronized void compare() {
        if (value != value) {
            System.out.println("WTF?");
        }
    }
}

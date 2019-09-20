package chapter1.cretans;

/* threadsafe ?*/
public class SynchronizedCretan4 extends CretanAttack {

    public double value;


    public SynchronizedCretan4(double value) {
        super(value);
    }

    public static void main(String[] args) {
        SynchronizedCretan4 instance = new SynchronizedCretan4(0);
        instance.test();
    }

    public void update() {
        synchronized (new String()) {
            value++;
        }
    }

    public void compare() {
        synchronized (new String()) {
            if (value != value) {
                System.out.println("WTF?");
            }
        }
    }
}

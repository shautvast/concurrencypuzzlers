package chapter1.cretans;

public class SynchronizedCretan3 extends CretanAttack {

    public double value;


    public SynchronizedCretan3(double value) {
        super(value);
    }

    public static void main(String[] args) {
        SynchronizedCretan3 instance = new SynchronizedCretan3(0);
        instance.test();
    }

    public void update() {
        synchronized ("") {
            value++;
        }
    }

    public void compare() {
        synchronized ("") {
            if (value != value) {
                System.out.println("WTF?");
            }
        }
    }
}

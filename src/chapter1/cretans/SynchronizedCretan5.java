package chapter1.cretans;

/* threadsafe? */
public class SynchronizedCretan5 {

    private static SynchronizedCretan5 instance = new SynchronizedCretan5(0);
    public double value;

    public SynchronizedCretan5(double value) {
        this.value = value;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (; ; ) {
                instance.update();
            }
        }).start();

        new Thread(() -> {
            for (; ; ) {
                instance.compare();
            }
        }).start();
    }

    public synchronized void update() {
        instance=new SynchronizedCretan5(value++);
    }

    public synchronized void compare() {
        if (value != value) {
            System.out.println("WTF?");
        }
    }
}

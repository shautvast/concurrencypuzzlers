package chapter3;

public class VolatileCretan {

    private static VolatileCretan instance = new VolatileCretan(0);
    public volatile double value;


    public VolatileCretan(double value) {
        this.value = value;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (; ; ) {
                instance.value++;
            }
        }).start();

        new Thread(() -> {
            for (; ; ) {
                instance.test();
            }
        }).start();

    }

    public void test() {
        if (value != value) {
            System.out.println("WTF?");
        }
    }
}

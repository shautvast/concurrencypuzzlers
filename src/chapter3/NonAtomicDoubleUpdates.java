package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class NonAtomicDoubleUpdates {

    static Thread t1, t2, t3;
    private static NonAtomicDoubleUpdates instance = new NonAtomicDoubleUpdates(0);
    private static boolean running = true;
    private final List<Double> allValues = new CopyOnWriteArrayList<>();
    public double value;
    private static Random random=new Random();

    public NonAtomicDoubleUpdates(double value) {
        this.value = value;
    }

    public static void main(String[] args) {

        t1 = new Thread(() -> {
            for (; running; ) {
                instance.update();
            }
        });
        t2 = new Thread(() -> {
            for (; running; ) {
                instance.update();
            }
        });

        t1.start();
        t2.start();
        for (; running; ) {
            instance.test();
        }
    }

    public void update() {
        value=Math.abs(random.nextInt(Integer.MAX_VALUE));
        allValues.add(value);
    }

    public void test() {
        try {
            if (value != value) {
                System.out.println("WTF?");
                running = false;
                t1.join();
                t2.join();
                System.out.println(allValues);
                System.exit(-1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

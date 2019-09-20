package chapter3;

public class Visibility {

    private static boolean ready;
    private static int value;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(value);
        }).start();

        value = 42;
        ready = true;
    }

}

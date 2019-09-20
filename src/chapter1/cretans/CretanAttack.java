package chapter1.cretans;

public class CretanAttack {

    public double value;
    private final Cretan cretan=new Cretan(0);

    public CretanAttack(double value) {
        this.value = value;
    }

    public static void main(String[] args) {
        CretanAttack instance = new CretanAttack(0);
        instance.test();

    }

    public void test() {
        new Thread(() -> {
            for (; ; ) {
                update();
            }
        }).start();

        new Thread(() -> {
            for (; ; ) {
                compare();
            }
        }).start();
    }

    public void update() {
        value++;
    }

    public void compare() {
        if (value != value) { // this program is a liar
            System.out.println("WTF?");
        }
    }
}

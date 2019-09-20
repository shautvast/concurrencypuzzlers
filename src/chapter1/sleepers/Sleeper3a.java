package chapter1.sleepers;

public class Sleeper3a {

    /* or use Object.wait */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("sleep");

        Sleeper3a sleeper = new Sleeper3a();

        synchronized (sleeper) { // synchronized is mandatory
            sleeper.wait(1000);
        }

        System.out.println("wake up");
    }
}

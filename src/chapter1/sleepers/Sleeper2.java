package chapter1.sleepers;

import java.util.concurrent.TimeUnit;

public class Sleeper2 {

    /* now the unit is clear*/
    public static void main(String[] args) throws InterruptedException {
        System.out.println("sleep");

        TimeUnit.SECONDS.sleep(1);

        System.out.println("wake up");
    }
}

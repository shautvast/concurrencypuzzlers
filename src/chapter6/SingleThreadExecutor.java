package chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i=0; i<10; i++){
            final int count = i;
            executor.submit(()-> print(count));
        }
    }

    private static void print(int value){
        System.out.println(value);
    }
}

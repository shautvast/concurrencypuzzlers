package chapter8;

import java.util.stream.IntStream;

public class ParallelStreams {

    public static void main(String[] args) {
        IntStream.range(0, 8)
                .parallel()
                .forEach((i) -> {
                    System.out.printf("thread %s, value %d%n",Thread.currentThread().getName(),i);
                });
    }
}

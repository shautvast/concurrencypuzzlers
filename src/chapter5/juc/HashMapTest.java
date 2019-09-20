package chapter5.juc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/*
 * https://stackoverflow.com/questions/18542037/how-to-prove-that-hashmap-in-java-is-not-thread-safe
 */
public class HashMapTest {

    @Test
    public void test() {
        Map<Integer, String> map = new HashMap<>();

        Integer targetKey = Integer.MAX_VALUE;
        String targetValue = "v";
        map.put(targetKey, targetValue);

        new Thread(() -> {
            IntStream.range(0, targetKey).forEach(key -> map.put(key, "someValue"));
        }).start();


        while (true) {
            if (!targetValue.equals(map.get(targetKey))) {
                System.out.printf("Current size of map: %d",map.size());
                throw new RuntimeException("HashMap is not thread safe.");
            }
        }
    }
}

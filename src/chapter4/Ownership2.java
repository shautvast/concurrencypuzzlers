package chapter4;

import java.util.HashMap;
import java.util.Map;

public class Ownership2 {
    private final static Map<String, Car> cars = new HashMap<>();

    public static void main(String[] args) {
        Car car = new Car();
        cars.put("toyota", car);
        car.drive();
        //car is not garbage collected
    }

}

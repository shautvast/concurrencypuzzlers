package chapter4;

public class Ownership {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
        //car is garbage collected
    }

}

package chapter4;

public class Car {

    public void drive(){
        System.out.printf("Thread %s driving this car", Thread.currentThread());
    }
}

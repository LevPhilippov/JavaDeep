package Lesson1Generics;

public class Apple extends Fruit{
    private final double appleWeight = 1.0d;

    public Apple() {
    }

    @Override
    public double getWeight() {
        return appleWeight;
    }
}

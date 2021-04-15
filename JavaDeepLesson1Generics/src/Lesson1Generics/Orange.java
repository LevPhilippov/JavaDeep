package Lesson1Generics;

public class Orange extends Fruit{
    private final double orangeWeight = 1.5d;

    public Orange() {
    }

    @Override
    public double getWeight() {
        return orangeWeight;
    }
}

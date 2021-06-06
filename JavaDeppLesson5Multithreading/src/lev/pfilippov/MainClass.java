package lev.pfilippov;

import java.util.concurrent.CountDownLatch;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final CountDownLatch finishLatch = new CountDownLatch(CARS_COUNT);
    public static final CountDownLatch startlatch = new CountDownLatch(CARS_COUNT);
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }


        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        awaitAllCarsAreReady(startlatch);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        awaitAllCarsFinish();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        for (Car car : cars) {
            if (car.isWinner()) {
                System.out.printf("Участник %s победил!", car.getName());
                break;
            }
        }
    }

    private static void awaitAllCarsFinish() {
        try {
            finishLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void awaitAllCarsAreReady(CountDownLatch startLatch) {
        try {
            startLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
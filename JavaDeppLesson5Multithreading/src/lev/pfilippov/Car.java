package lev.pfilippov;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static CyclicBarrier readyBarrier;
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private boolean winner;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.winner = false;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        readyBarrier = new CyclicBarrier(CARS_COUNT);
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainClass.startlatch.countDown();
            readyBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        MainClass.finishLatch.countDown();
        if (!Race.doWeHaveAWinner) {
            Race.doWeHaveAWinner =true;
            winner = true;
        }
        try {
            readyBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public boolean isWinner() {
        return winner;
    }
}
package lev.pfilippov.exec;

import java.util.concurrent.*;

public class Exec {
    public static void main(String[] args) {

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setDaemon(true);
                return t;
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(() -> {
            System.out.println("Start sleepeng!");
            Thread.sleep(5000);
            System.out.println("Sleep is finished!");
            return "Finish of callable operation!";
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Finish of  all threads!");
    }
}

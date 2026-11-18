package day_06;

public class ThreadMemoryDemo {

    private static int sharedCount = 0;

    public static void run() throws InterruptedException {

        sharedCount = 0;

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                sharedCount++;
            }
        };

        Thread first = new Thread(task, "Worker-1");
        Thread second = new Thread(task, "Worker-2");

        first.start();
        second.start();

        first.join();//replace above
        second.join();

        System.out.println("Expected count: 2000");
        System.out.println("Actual count: " + sharedCount);
    }
}

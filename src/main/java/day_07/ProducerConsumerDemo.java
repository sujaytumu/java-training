package day_07;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerDemo {
    //visibility b/w threads
    private static volatile boolean running = true;
    //t-safe commun
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    //
    public static void main(String[] args) throws Exception {

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int id = 101; id <= 105; id++) {

                    queue.put(id);

                    System.out.println("Produced: " + id);
                }

                // Signal completion
                running = false;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                while (running || !queue.isEmpty()) {

                    Integer id = queue.poll(
                            200, TimeUnit.MILLISECONDS
                    );

                    if (id != null) {
                        System.out.println("Processed: " + id);
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        // Wait for both threads
        producer.join();
        consumer.join();

        System.out.println("All requests completed.");
    }




}

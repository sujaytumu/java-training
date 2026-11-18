package day_06;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== MEMORY AND REFERENCES =====");
        for (int i = 1; i <= 1000; i++) {
            MemoryDemo.run();
            Thread.sleep(100);
        }

//
//        System.out.println("\n===== GARBAGE COLLECTION =====");
//        GarbageCollectionDemo.run();
//
//        System.out.println("\n===== THREAD EXECUTION =====");
//        ThreadMemoryDemo.run();
    }
}

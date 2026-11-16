package day_06;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("===== MEMORY AND REFERENCES =====");
        MemoryDemo.run();

        System.out.println("\n===== GARBAGE COLLECTION =====");
        GarbageCollectionDemo.run();

        System.out.println("\n===== THREAD EXECUTION =====");
        ThreadMemoryDemo.run();
    }
}

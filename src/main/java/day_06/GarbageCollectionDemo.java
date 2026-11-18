package day_06;

import java.util.ArrayList;
import java.util.List;

public class GarbageCollectionDemo {

    public static void run() {

        List<byte[]> retainedData = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            retainedData.add(new byte[1024 * 1024]);
        }

        System.out.println("Retained blocks: "
                + retainedData.size());

        retainedData.clear();//only ref gone, not mem

        System.out.println("After clear: "
                + retainedData.size());

        System.gc();

        System.out.println("GC requested");
    }
}

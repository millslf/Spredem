package za.co.jefdev.messenger.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

    public static
    <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    public static void runThreads(List<Thread> threadList) {
        for (Thread thread : threadList) {
            thread.run();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

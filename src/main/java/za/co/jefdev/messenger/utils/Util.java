package za.co.jefdev.messenger.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Util {

    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }

    public static boolean runThreads(List<Runnable> threadList) throws InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        for (Runnable thread : threadList) {
            es.execute(thread);
        }
        es.shutdown();
        return es.awaitTermination(5, TimeUnit.MINUTES);
    }
}

package za.co.jefdev;

import za.co.jefdev.messenger.utils.Rest;
import za.co.jefdev.messenger.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class testGCP {

    public static void main(String[] args) throws Exception {
        List<Runnable> threadlist = new ArrayList<>();
        for(int i=0;i<1000;i++) {
            threadlist.add(() -> {
                String string = null;
                try {
//                    string = Rest.makeEasyRequest("http://35.225.5.8/");
                    string = Rest.makeEasyRequest("https://static-dock-193914.appspot.com/demo");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(string);
            });
        }
        Long startt = System.currentTimeMillis();
        Util.runThreads(threadlist);
        Long endt = System.currentTimeMillis();
        System.out.println("Runtime in ms: " + (endt - startt));

    }

//    public static void main(String[] args) throws IOException {
//        List<Thread> threadlist = new ArrayList<>();
//        for(int i=0;i<5000;i++) {
//            new Thread(() -> {
//                String string = null;
//                try {
//                    string = Rest.makeRequest("https://static-dock-193914.appspot.com/demo");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + string);
//            }).start();
//        }

//
//
//    }
}

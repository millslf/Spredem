package za.co.jefdev.utils;

public class InstantiateClasses implements Runnable {

    Class aClass = null;

    public InstantiateClasses(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public void run() {
        try {
            aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

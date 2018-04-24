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
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

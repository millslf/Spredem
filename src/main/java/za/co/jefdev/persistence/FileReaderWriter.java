package za.co.jefdev.persistence;

import java.io.*;

public class FileReaderWriter {

    public static void persistEntities(Object... entity) throws IOException, ClassNotFoundException {
        for(Object obj:entity) {
            FileOutputStream f = new FileOutputStream(new File(obj.getClass().getName().toString()));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(obj);

            o.close();
            f.close();
        }
    }

    public static Object loadValues(String entityName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File(entityName));
            oi = new ObjectInputStream(fi);

            // Read objects
             return oi.readObject();

        } catch (IOException | ClassNotFoundException e) {
            Class cls = Class.forName(entityName);
            return cls.newInstance();
        } finally {
            if(oi != null) {
                oi.close();
                fi.close();
            }
        }
    }

}

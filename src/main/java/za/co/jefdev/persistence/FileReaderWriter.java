package za.co.jefdev.persistence;

import java.io.*;

public class FileReaderWriter {

    public static void persistEntities(SpreadEntity entity) throws IOException, ClassNotFoundException {
        FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        o.writeObject(entity);

        o.close();
        f.close();
    }

    public static SpreadEntity loadValues() throws IOException {
        FileInputStream fi = null;
        SpreadEntity oldSpreadVals;
        ObjectInputStream oi = null;
        try {
            fi = new FileInputStream(new File("myObjects.txt"));
            oi = new ObjectInputStream(fi);

            // Read objects
             return oldSpreadVals = (SpreadEntity) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return oldSpreadVals = new SpreadEntity();
        } finally {
            if(oi != null) {
                oi.close();
                fi.close();
            }
        }
    }

}

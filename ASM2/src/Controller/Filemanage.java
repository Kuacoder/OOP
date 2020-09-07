package Controller;

import Models.Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Filemanage {
    public static Boolean saveToFile(Object objects, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(objects);
            out.close();
            fileOut.close();
            System.out.println("Lưu file vào "+filePath+" thành công");
            return true;
        } catch (Exception e) {
            System.err.println("Lưu file vào "+filePath+" thất bại: "+e.toString());
            return false;
        }
    }
    public static ArrayList<Model> readFromFile(String filePath){
        ArrayList<Model> objects = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object list = in.readObject();
            if(list instanceof ArrayList<?>) {
                for(Object item: (ArrayList<?>)list) {
                    if(item instanceof Model) {
                        objects.add((Model) item);
                    }
                }
            }
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.err.println("Đọc file "+filePath+" thất bại: "+e.toString());
        } finally {
            return objects;
        }
    }
}

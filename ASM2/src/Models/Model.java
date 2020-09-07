package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Model implements Serializable {
    public abstract void input();
    public abstract void update(ArrayList list2);
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

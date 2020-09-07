import Controller.Billmanage;
import Controller.Customermanage;
import Controller.Itemmanage;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Itemmanage itemmanage = new Itemmanage();
        Customermanage customermanage = new Customermanage();
        Billmanage billmanage = new Billmanage();
        itemmanage.customermanage = customermanage;
        customermanage.itemmanage=itemmanage;
        billmanage.customermanage=customermanage;
        billmanage.itemmanage=itemmanage;
        int choice = -1;
        while (choice != 0) {
            System.out.println(" ");
            System.out.println("======================================= ");
            System.out.println("|      Chào mừng đến với cửa hàng     |");
            System.out.println("=======================================");
            System.out.println("| 1. Quản lý danh sách mặt hàng       | ");
            System.out.println("| 2. Quản lý khách hàng               | ");
            System.out.println("| 3. Quản lý hóa đơn                  | ");
            System.out.println("| 0. Thoát                            |");
            System.out.println("=======================================");
            try {
                System.out.printf("#Chọn: ");
                choice = getScanner().nextInt();
                switch (choice) {
                    case 1:
                        itemmanage.showMenu();
                        break;
                    case 2:
                        customermanage.showMenu();
                        break;
                    case 3:
                        billmanage.showMenu();
                        break;
                    default:
                        if (choice != 0 && choice > 3) {
                            System.err.println("Bạn đã chọn sai vui lòng chọn lại từ (0-3)!");
                        }
                        break;
                }
            } catch (Exception e) {
                System.err.println("Bạn đã chọn sai vui lòng chọn lại từ (0-3)!");
            }
        }
        System.out.println("Cảm ơn quý khách.");
    }
    private static Scanner getScanner() {
        return new Scanner(System.in);
    }
}